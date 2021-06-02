package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(name = "OrderServlet", value = "/orderArticles")
public class OrderServlet extends HttpServlet {

    private PrintWriter out;
    private Shop onlineShop;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Integer customerId;

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        onlineShop = Shop.create();
        priceFormatter = new DecimalFormat("0.00€");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.request = request;
        this.response = response;
        this.out = response.getWriter();

        RequestDispatcher bannerRequestDispatcher = request.getRequestDispatcher("/showBanner");

        String action = request.getParameter("action");

        out.println("<html>");

        printHead();

        out.println("<body>");

        bannerRequestDispatcher.include(request, response);

        printCartAndOrderForm();

        printFooter();

        out.println("</body>");

        out.println("</html>");

        out.close();
    }

    private void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Order</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"order.css\" rel=\"stylesheet\">"
                + "</head>");
    }

    private void printCartAndOrderForm() {
        printCart();
        printOrderForm();
    }

    private void printCart() {
        //TODO
    }

    private void printOrderForm() {
        //TODO
    }

    private void printFooter() {

    }

    private Integer getCustomerIdFromSession(HttpServletRequest request) {
        return 0;
    }
}
