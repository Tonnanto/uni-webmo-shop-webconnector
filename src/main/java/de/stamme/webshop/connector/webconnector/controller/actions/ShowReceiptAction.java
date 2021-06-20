package de.stamme.webshop.connector.webconnector.controller.actions;


import de.leuphana.shop.structure.Order;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "ShowReceiptAction", value = "/showReceiptAction")
public class ShowReceiptAction extends WebshopAction {
    @Override
    protected String doAction() {

        int customerId = getCustomerIdFromSession();
        Order order = onlineShop.checkOutCart(customerId);

        request.setAttribute("order", order);
        return "/showReceipt";
    }
}
