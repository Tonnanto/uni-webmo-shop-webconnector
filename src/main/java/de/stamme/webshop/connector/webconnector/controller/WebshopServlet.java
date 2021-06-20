package de.stamme.webshop.connector.webconnector.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WebshopServlet", value = "/WebshopServlet")
public abstract class WebshopServlet extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected PrintWriter out;

    public WebshopServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.out = response.getWriter();
        this.request = request;
        this.response = response;

        printContent();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.out = response.getWriter();
        this.request = request;
        this.response = response;

        printContent();
        out.close();
    }

    private void printContent() throws ServletException, IOException {
        RequestDispatcher bannerRequestDispatcher = request.getRequestDispatcher("/showBanner");

        out.println("<html>");

        printHead();

        out.println("<body>");
        bannerRequestDispatcher.include(request, response);
        printBody();
        printFooter();
        out.println("</body>");

        out.println("</html>");

    }

    protected void printHead() {}

    protected abstract void printBody();

    protected void printFooter() {

    }
}
