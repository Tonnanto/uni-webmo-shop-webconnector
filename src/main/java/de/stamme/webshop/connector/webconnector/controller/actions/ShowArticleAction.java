package de.stamme.webshop.connector.webconnector.controller.actions;

import de.leuphana.shop.behaviour.Shop;
import de.leuphana.shop.structure.Article;
import jakarta.servlet.annotation.WebServlet;


@WebServlet(name = "ShowArticleAction", value = "/showArticleAction")
public class ShowArticleAction extends WebshopAction {
    @Override
    protected String doAction() {
        String articleIdAsString = request.getParameter("articleId");

        Shop shop = Shop.create();
        int articleId = Integer.parseInt(articleIdAsString);
        Article article = shop.getArticle(articleId);

        request.setAttribute("article", article);
        return "/showArticle";
    }
}
