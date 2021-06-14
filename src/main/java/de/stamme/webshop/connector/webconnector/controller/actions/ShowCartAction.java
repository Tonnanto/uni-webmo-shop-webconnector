package de.stamme.webshop.connector.webconnector.controller.actions;


import de.leuphana.shop.structure.Cart;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("showCartAction")
public class ShowCartAction extends WebshopAction {
    @Override
    protected String doAction() {

        int customerId = getCustomerIdFromSession();
        Cart cart = onlineShop.getCartForCustomer(customerId);

        request.setAttribute("cart", cart);
        return "/showCart";
    }
}
