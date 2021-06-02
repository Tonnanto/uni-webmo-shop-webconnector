package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ReceiptServlet", value = "/showReceipt")
public class ReceiptServlet extends HttpServlet {

    private PrintWriter out;
    private Shop onlineShop;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Integer customerId;

    @Override
    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void printHead() {

    }

    private void printReceipt() {

    }

    private void printFooter() {

    }

    private Integer getCustomerIdFromSession(HttpServletRequest request) {
        return 0;
    }
}
