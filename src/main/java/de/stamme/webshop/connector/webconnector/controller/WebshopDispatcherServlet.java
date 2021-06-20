package de.stamme.webshop.connector.webconnector.controller;

import de.stamme.webshop.connector.webconnector.controller.actions.WebshopActionEnum;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dispatchAction")
public class WebshopDispatcherServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        evaluateAction();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        evaluateAction();
    }

    private void evaluateAction() throws ServletException, IOException {
        String action = request.getParameter("action");
        WebshopActionEnum webshopActionEnum = WebshopActionEnum.valueOf(action);

        switch (webshopActionEnum) {
            case SHOW_ARTICLE:
                dispatch2Action("/showArticleAction");
                break;
            case SHOW_CATALOG:
                dispatch2Action("/showCatalogAction");
                break;
            case SHOW_CART:
                dispatch2Action("/showCartAction");
                break;
            case SHOW_RECEIPT:
                dispatch2Action("/showReceiptAction");
                break;
            case ADD_ARTICLE:
                dispatch2Action("/addArticleAction");
                break;
            case REMOVE_ARTICLE:
                dispatch2Action("/removeArticleAction");
                break;
            case ORDER_ARTICLE:
                dispatch2Action("/orderArticleAction");
                break;
        }
    }

    private void dispatch2Action(String actionName) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(actionName);
        requestDispatcher.forward(request, response);
    }
}
