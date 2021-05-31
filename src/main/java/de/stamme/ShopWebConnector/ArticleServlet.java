package de.stamme.ShopWebConnector;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Article;
import de.leuphana.shop.structure.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ArticleServlet", value = "/showArticle")
public class ArticleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Shop onlineShop;
    private PrintWriter out;
    private DecimalFormat priceFormatter;

    // TODO: init method

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        priceFormatter = new DecimalFormat("0.00€");
        onlineShop = Shop.create();
        out = response.getWriter();

        RequestDispatcher bannerRequestDispatcher = request.getRequestDispatcher("/showBanner");

        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            Article article = onlineShop.getArticle(articleId);
            if (article == null) return;


            out.println("<html>");
            printHead();

            out.println("<body>");
            bannerRequestDispatcher.include(request, response);
            printArticle(article);
            printFooter();
            out.println("</body>");

            out.println("</html>");
            out.close();

        } catch (NumberFormatException e) {
            System.out.println("Invalid article ID: " + request.getParameter("articleId"));
        }
    }

    private void printHead() {
        out.println("<head>"
                + "<title>Catalog</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"article.css\" rel=\"stylesheet\">"
                + "</head>");
    }


    private void printArticle(Article article) {
        // Holds the product details
        Map<String, String> details = new HashMap<>();

        details.put("Price", priceFormatter.format(article.getPrice()));

        if (article instanceof Book) {
            Book book = (Book) article;
            details.put("Author", (book.getAuthor() != null) ? book.getAuthor() : "Unknown");
            details.put("Release Year", "" + book.getReleaseYear());
            details.put("Category", book.getBookCategory().name().toLowerCase().replace("_", " "));

        } else {
            details.put("Manufactor", article.getManufactor());
        }

        // Article Showcase (image + description)
        out.println("<div class=\"article-showcase\">");

        if (article.getImageLocation() != null)
            out.printf("<img class=\"full-image\" src=\"%s\" alt=\"%s\">", article.getImageLocation(), article.getName());

        out.println("<div>");
        out.println("<h1 class=\"article-title\">" + article.getName() + "</h1>");
        out.println("<p class=\"article-description\">" + article.getDescription() + "</p>");
        out.println("</div>");
        out.println("</div>");

        // Article Details (table)
        out.println("<table>");
        for (Map.Entry<String, String> detail : details.entrySet()) {
            out.println("<tr>");
            out.printf(
                    "<td>%s</td><td>%s</td>",
                    detail.getKey(), detail.getValue()
            );
            out.println("</tr>");
        }
        out.println("</table>");
    }

    private void printFooter() {

    }
}
