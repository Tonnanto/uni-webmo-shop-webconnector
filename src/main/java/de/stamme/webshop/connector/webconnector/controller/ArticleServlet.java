package de.stamme.webshop.connector.webconnector.controller;

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
public class ArticleServlet extends WebshopServlet {
    private static final long serialVersionUID = 1L;

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        priceFormatter = new DecimalFormat("0.00€");
    }


    @Override
    protected void printBody() {
        Article article = (Article) request.getAttribute("article");

        if (article != null)
            printArticle(article);
        else
            System.out.println("Invalid article ID: " + request.getParameter("articleId"));
    }


    @Override
    protected void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Article</title>"
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
}
