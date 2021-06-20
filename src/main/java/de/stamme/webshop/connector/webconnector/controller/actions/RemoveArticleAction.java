package de.stamme.webshop.connector.webconnector.controller.actions;


import de.leuphana.shop.structure.Cart;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "RemoveArticleAction", value = "/removeArticleAction")
public class RemoveArticleAction extends WebshopAction {
    @Override
    protected String doAction() {

        int customerId = getCustomerIdFromSession();

        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            onlineShop.decrementArticleQuantityInCart(customerId, articleId);

        } catch (NumberFormatException e) {
            System.out.println("Invalid article ID: " + request.getParameter("articleId"));
        }

        Cart cart = onlineShop.getCartForCustomer(customerId);
        request.setAttribute("cart", cart);
        return "/showCart";
    }
}
