package de.stamme.webshop.connector.webconnector.controller.actions;


import jakarta.servlet.annotation.WebServlet;

@WebServlet("removeArticleAction")
public class RemoveArticleAction extends WebshopAction {
    @Override
    protected String doAction() {

        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            int customerId = getCustomerIdFromSession();
            onlineShop.decrementArticleQuantityInCart(customerId, articleId);

        } catch (NumberFormatException e) {
            System.out.println("Invalid article ID: " + request.getParameter("articleId"));
        }

        return "/showCart";
    }
}
