package de.stamme.webshop.connector.webconnector.controller.actions;


import de.leuphana.shop.structure.Article;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "AddArticleAction", value = "/addArticleAction")
public class AddArticleAction extends WebshopAction {
    @Override
    protected String doAction() {
        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            Integer customerId = getCustomerIdFromSession();
            Article article = onlineShop.getArticle(articleId);

            onlineShop.addArticleToCart(customerId, articleId);

            request.setAttribute("addedArticle", article);

        } catch (NumberFormatException e) {
            System.out.println("Invalid article ID: " + request.getParameter("articleId"));
        }
        return "/showCatalog";
    }
}
