package de.stamme.webshop.connector.webconnector.controller.actions;


import de.leuphana.shop.structure.Cart;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "OrderArticleAction", value = "/orderArticleAction")
public class OrderArticleAction extends WebshopAction {
    @Override
    protected String doAction() {

        int customerId = getCustomerIdFromSession();
        Cart cart = onlineShop.getCartForCustomer(customerId);

        request.setAttribute("cart", cart);
        return "/orderArticles";
    }
}
