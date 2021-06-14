package de.stamme.webshop.connector.webconnector.controller.actions;


import jakarta.servlet.annotation.WebServlet;

@WebServlet("showCatalogAction")
public class ShowCatalogAction extends WebshopAction {
    @Override
    protected String doAction() {
        return "/showCatalog";
    }
}
