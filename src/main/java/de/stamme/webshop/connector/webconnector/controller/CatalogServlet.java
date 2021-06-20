package de.stamme.webshop.connector.webconnector.controller;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Article;
import jakarta.servlet.annotation.WebServlet;

import java.text.DecimalFormat;
import java.util.Set;

@WebServlet(name = "CatalogServlet", value = "/showCatalog")
public class CatalogServlet extends WebshopServlet {
    private static final long serialVersionUID = 1L;

    private DecimalFormat priceFormatter;

    @Override
    public void init() {
        priceFormatter = new DecimalFormat("0.00€");
    }

    @Override
    protected void printBody() {
        Article addedArticle = (Article) request.getAttribute("addedArticle");
        if (addedArticle != null)
            printAddedArticle(addedArticle);

        printCatalog();
    }

    protected void printHead() {
        out.println("<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>Catalog</title>"
                + "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap\" rel=\"stylesheet\">\r\n"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "<link href=\"catalog.css\" rel=\"stylesheet\">"
                + "</head>");
    }

    private void printCatalog() {
        Set<Article> catalog = Shop.create().getArticles();
        // Catalog (table)
        out.println("<table>");
        out.println("<tr><th></th><th>Catalog</th><th>Price</th></tr>");

        for (Article article : catalog) {

            String imageLocation = article.getImageLocation();
            int articleId = article.getArticleId();
            String name = article.getName();
            String price = priceFormatter.format(article.getPrice());

            out.println("<tr>");
            out.printf(
                    "<td><img class=\"thumbnail-image\" src=\"%s\"></td>" +
                    "<td><a href=\"./dispatchAction?action=SHOW_ARTICLE&articleId=%s\">%s</a></td>" +
                    "<td>%s</td>" +
                    "<td><a class=\"button\" href=\"./dispatchAction?action=ADD_ARTICLE&articleId=%s\">Add to cart</a></td>",
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
}