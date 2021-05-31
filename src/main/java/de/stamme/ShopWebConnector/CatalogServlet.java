package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Article;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(name = "CatalogServlet", value = "/showCatalog")
public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Shop onlineShop;
    private PrintWriter out;
    private HttpServletRequest request;
    private HttpServletResponse response;

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

        // Add to Cart Notification
        if (action != null && action.equals("addArticle")) {
            try {
                int articleId = Integer.parseInt(request.getParameter("articleId"));
                Integer customerId = getCustomerIdFromSession(request);
                Article article = onlineShop.getArticle(articleId);

                onlineShop.addArticleToCart(customerId, articleId);

                printAddedArticle(article);

            } catch (NumberFormatException e) {
                System.out.println("Invalid article ID: " + request.getParameter("articleId"));
            }
        }

        printCatalog();

        printFooter();

        out.println("</body>");

        out.println("</html>");

        out.close();
    }

    private Integer getCustomerIdFromSession(HttpServletRequest request) {
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

    private void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Catalog</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"catalog.css\" rel=\"stylesheet\">"
                + "</head>");
    }

    private void printCatalog() {
        // Catalog (table)
        out.println("<table>");
        out.println("<tr><th></th><th>Catalog</th><th>Price</th></tr>");

        for (Article article : onlineShop.getArticles()) {

            String imageLocation = article.getImageLocation();
            int articleId = article.getArticleId();
            String name = article.getName();
            String price = priceFormatter.format(article.getPrice());

            out.println("<tr data-href=\"./showArticle?articleId=%s\">");
            out.printf(
                    "<td><img class=\"thumbnail-image\" src=\"%s\"></td><td><a href=\"./showArticle?articleId=%s\">%s</a></td><td>%s</td><td>"
                            + "<a class=\"button\" href=\"./showCatalog?action=addArticle&articleId=%s\">Add to cart</a></td>",
                    imageLocation, articleId, name, price, articleId
            );
            out.println("</tr>");
        }

        out.println("</table>");
    }

    private void printAddedArticle(Article article) {
        if (article == null) return;
        out.println("<div class=\"added-to-cart-popup\"><p>Added to cart:<br>" + article.getName() + "</p></div>");
    }

    private void printFooter() {
        // TODO
    }

    private String encodeURL(String url) {
        // TODO
        return url;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}