package de.stamme.webshop.connector.webconnector.controller;

import de.leuphana.shop.structure.Order;
import de.leuphana.shop.structure.OrderPosition;
import jakarta.servlet.annotation.WebServlet;

import java.text.DecimalFormat;

@WebServlet(name = "ReceiptServlet", value = "/showReceipt")
public class ReceiptServlet extends WebshopServlet {

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        priceFormatter = new DecimalFormat("0.00€");
    }

    @Override
    protected void printBody() {
        Order order = (Order) request.getAttribute("order");

        if (order != null) {
            out.println("<div class=\"main-content\">");
            printReceipt(order);
            out.println("</div>");
        }
        else
            System.out.println("Order not found");
    }


    protected void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Receipt</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"cart.css\" rel=\"stylesheet\">"
                + "</head>");
    }

    private void printReceipt(Order order) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String creditCardNumber = request.getParameter("creditCardNumber");

        out.printf("<h2>%s items have successfully been ordered for %s %s.</h2>", order.getNumberOfArticles(), firstName, lastName);


        // CartItems
        out.println("<table>");
        out.println("<tr><th></th><th>Cart</th><th>Amount</th><th>Price</th></tr>");

        for (OrderPosition orderPosition : order.getOrderPositions().values()) {

            String imageLocation = orderPosition.getArticle().getImageLocation();
            int articleId = orderPosition.getArticle().getArticleId();
            String name = orderPosition.getArticle().getName();
            int quantity = orderPosition.getArticleQuantity();
            String price = priceFormatter.format(orderPosition.getArticle().getPrice());

            out.println("<tr data-href=\"./showArticle?articleId=%s\">");
            out.println(String.format(
                    "<td><img class=\"thumbnail-image\" src=\"%s\"></td><td><a href=\"./showArticle?articleId=%s\">%s</a><td>%s pcs.</td></td><td>%s</td>",
                    imageLocation, articleId, name, quantity, price
            ));
            out.println("</tr>");
        }

        out.println("<tfoot>");

        // Total Price Footer
        out.println("<tr><td></td><td></td>\r\n"
                + "<td>Total:</td>\r\n"
                + "<td>" + priceFormatter.format(order.getTotalPrice()) + "</td><td></td>\r\n"
                + "</tr>");

        out.println("</tfoot>");

        out.println("</table>");

        out.println("<h2>Thank you!</h2>");

    }
}
