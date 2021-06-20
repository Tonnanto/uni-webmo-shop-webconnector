package de.stamme.webshop.connector.webconnector.controller.actions;

import de.leuphana.shop.behaviour.Shop;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public abstract class WebshopAction extends HttpServlet {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Shop onlineShop;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        this.onlineShop = Shop.create();

        executeAction();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        this.onlineShop = Shop.create();

        executeAction();
    }

    private void executeAction() throws ServletException, IOException {
        String urlForView = doAction();
        dispatch2View(urlForView);
    }

    private void dispatch2View(String urlForView) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(urlForView);
        requestDispatcher.forward(request, response);
    }

    protected abstract String doAction();

    protected Integer getCustomerIdFromSession() {
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
}
