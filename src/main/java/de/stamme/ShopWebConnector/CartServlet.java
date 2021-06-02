package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Article;
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

@WebServlet(name = "CartServlet", value = "/showCart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Shop onlineShop;
    private PrintWriter out;
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

        this.response = response;
        this.request = request;
        this.out = response.getWriter();
        this.customerId = getCustomerIdFromSession(request);

        RequestDispatcher bannerRequestDispatcher = request.getRequestDispatcher("/showBanner");

        String action = request.getParameter("action");

        out.println("<html>");
        printHead();

        out.println("<body>");
        bannerRequestDispatcher.include(request, response);

        // Remove article from cart
        if (action != null && action.equals("removeArticle")) {
            try {
                int articleId = Integer.parseInt(request.getParameter("articleId"));
                decrementCartItemQuantity(articleId);

            } catch (NumberFormatException e) {
                System.out.println("Invalid article ID: " + request.getParameter("articleId"));
            }
        }

        printCart(onlineShop.getCartForCustomer(customerId));

        printFooter();
        out.println("</body>");

        out.println("</html>");
        out.close();
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

    private void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Catalog</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"cart.css\" rel=\"stylesheet\">"
                + "</head>");
    }


    private void printCart(Cart cart) {

        if (cart == null || cart.getCartItems().size() == 0) {
            out.println("<p class=\"cart-empty-message\">Your cart is empty.</p>");
            return;
        }

        // CartItems
        out.println("<table>");
        out.println("<tr><th></th><th>Cart</th><th>Amount</th><th>Price</th></tr>");

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

        out.println("<a class=\"order-button\" href=\"./orderArticles\">Order Articles</a>");
    }

    private void printFooter() {


    }

    private String encodeUrl(String url) {
        // TODO: encode URL
        return url;
    }

    private void decrementCartItemQuantity(Integer articleId) {
        Integer customerId = getCustomerIdFromSession(request);
        onlineShop.decrementArticleQuantityInCart(customerId, articleId);
    }
}
