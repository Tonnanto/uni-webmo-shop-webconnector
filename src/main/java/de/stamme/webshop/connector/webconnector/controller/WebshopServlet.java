package de.stamme.webshop.connector.webconnector.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "WebshopServlet", value = "/WebshopServlet")
public abstract class WebshopServlet extends HttpServlet {

    public WebshopServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void printContent() {

    }

    protected void printHeader() {

    }

    protected abstract void printBody();

    protected void printFooter() {

    }
}
