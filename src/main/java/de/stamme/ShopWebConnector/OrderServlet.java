package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Cart;
import de.leuphana.shop.structure.CartItem;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(name = "OrderServlet", value = "/orderArticles")
public class OrderServlet extends HttpServlet {

    private PrintWriter out;
    private Shop onlineShop;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Integer customerId;

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        onlineShop = Shop.create();
        priceFormatter = new DecimalFormat("0.00€");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.request = request;
        this.response = response;
        this.out = response.getWriter();
        this.customerId = getCustomerIdFromSession(request);

        RequestDispatcher bannerRequestDispatcher = request.getRequestDispatcher("/showBanner");

        out.println("<html>");

        printHead();

        out.println("<body>");

        bannerRequestDispatcher.include(request, response);

        out.println("<div class=\"main-content\">");
        printCartAndOrderForm();
        out.println("</div>");

        printFooter();

        out.println("</body>");

        out.println("</html>");

        out.close();
    }

    private void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Order</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"cart.css\" rel=\"stylesheet\">"
                + "</head>");
    }

    private void printCartAndOrderForm() {
        Cart cart = onlineShop.getCartForCustomer(this.customerId);

        // Check if items in cart
        if (cart == null || cart.getNumberOfArticles() == 0) {
            out.println("<p class=\"cart-empty-message\">Your cart is empty.</p>");
            return;
        }

        printCart(cart);
        printOrderForm();
    }

    private void printCart(Cart cart) {

        Integer numberOfArticles = cart.getNumberOfArticles();
        out.printf("<h3>You have %s articles in your cart:<h3>", numberOfArticles);

        out.println("<table>");

        out.println("<tr><th></th><th></th><th>Amount</th><th>Price</th></tr>");

        for (CartItem cartItem : cart.getCartItems()) {

            String imageLocation = cartItem.getArticle().getImageLocation();
            int articleId = cartItem.getArticle().getArticleId();
            String name = cartItem.getArticle().getName();
            int quantity = cartItem.getQuantity();
            String price = priceFormatter.format(cartItem.getArticle().getPrice());

            out.println("<tr data-href=\"./showArticle?articleId=%s\">");
            out.println(String.format(
                    "<td><img class=\"thumbnail-image\" src=\"%s\"></td><td><a href=\"./showArticle?articleId=%s\">%s</a><td>%s pcs.</td></td><td>%s</td><td>"
                            + "<a class=\"button\" href=\"./showCart?action=removeArticle&articleId=%s\">Remove from cart</a></td>",
                    imageLocation, articleId, name, quantity, price, articleId
            ));
            out.println("</tr>");
        }

        out.println("<tfoot>");

        // Total Price Footer
        out.println("<tr><td></td><td></td>\r\n"
                + "<td>Total:</td>\r\n"
                + "<td>" + priceFormatter.format(cart.getTotalPrice()) + "</td><td></td>\r\n"
                + "</tr>");

        out.println("</tfoot>");

        out.println("</table>");
    }

    private void printOrderForm() {
        out.println("<div class=\"order-form\">");
        out.println("<h3>Please enter your information here:</h3>");

        out.println("<form action=\"./showReceipt\" method=post>");

        out.println("<label for=\"firstName\">First name:</label><br>");
        out.println("<input type=\"text\" id=\"firstName\" name=\"firstName\" value=\"\"><br>");
        out.println("<label for=\"lastName\">Last name:</label><br>");
        out.println("<input type=\"text\" id=\"lastName\" name=\"lastName\" value=\"\"><br>");
        out.println("<label for=\"creditCardNumber\">Credit Card Number:</label><br>");
        out.println("<input type=\"number\" id=\"creditCardNumber\" name=\"creditCardNumber\" value=\"\"><br><br>");

        out.println("<input type=\"submit\" value=\"Submit\">");

        out.println("</form>");

        out.println("</div>");
    }

    private void printFooter() {

    }

    private Integer getCustomerIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer customerId = null;

        if (session.getAttribute("customerId") instanceof Integer)
            customerId = (Integer) session.getAttribute("customerId");

        if (customerId == null) {
            customerId = onlineShop.createCustomerWithCart();
            session.setAttribute("customerId", customerId);
        }

        return customerId;
    }
}
