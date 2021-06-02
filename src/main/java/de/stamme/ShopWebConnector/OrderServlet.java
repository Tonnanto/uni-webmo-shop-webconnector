package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OrderServlet", value = "/showOrder")
public class OrderServlet extends HttpServlet {

    private PrintWriter out;
    private Shop onlineShop;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Integer customerId;

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void printHead() {

    }

    private void printCartAndOrderForm() {

    }

    private void printCart() {

    }

    private void printOrderForm() {

    }

    private void printFooter() {

    }

    private Integer getCustomerIdFromSession(HttpServletRequest request) {
        return 0;
    }
}
