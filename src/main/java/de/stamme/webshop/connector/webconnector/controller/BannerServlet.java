package de.stamme.webshop.connector.webconnector.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BannerServlet", value = "/showBanner")
public class BannerServlet extends WebshopServlet {
    private static final long serialVersionUID = 1L;

    protected PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();

        response.setContentType("text/html");
        printBanner();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();

        response.setContentType("text/html");
        printBanner();
    }

    @Override
    protected void printBody() {

    }

    private void printBanner() {
        out.println("<nav>");

        // List of Navigation items
        String shopName = "Book Shop";
        out.println("<div class=\"logo\">\r\n"
                + "		<h1>" + shopName + "<span class=\"blink\">_</span></h1>\r\n"
                + "	</div>\r\n"
                + "	<ul class=\"nav-links\">\r\n"
                + "		<li>\r\n"
                + "			<a href=\"./dispatchAction?action=SHOW_CATALOG\">Catalog</a>\r\n"
                + "		</li>\r\n"
                + "		<li>\r\n"
                + "			<a href=\"./dispatchAction?action=SHOW_CART\">Cart</a>\r\n"
                + "		</li>\r\n"
                + "	</ul>\r\n");


        out.println("</nav>");
    }
}
