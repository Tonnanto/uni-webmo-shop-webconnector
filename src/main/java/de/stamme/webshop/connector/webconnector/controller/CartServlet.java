package de.stamme.webshop.connector.webconnector.controller;

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
public class CartServlet extends WebshopServlet {
    private static final long serialVersionUID = 1L;

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        priceFormatter = new DecimalFormat("0.00€");
    }


    @Override
    protected void printBody() {
        Cart cart = (Cart) request.getAttribute("cart");

        if (cart != null)
            printCart(cart);
        else
            System.out.println("Cart not found");
    }


    @Override
    protected void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Cart</title>"
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

    private void printDecrementCartItemQuantity(Article article) {

    }
}
