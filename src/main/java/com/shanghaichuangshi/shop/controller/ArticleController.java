package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Article;
import com.shanghaichuangshi.shop.service.ArticleService;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;

import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {

    private final ArticleService articleService = new ArticleService();
    private final CategoryService categoryService = new CategoryService();

    @ActionKey(Url.ARTICLE_STORY_LIST)
    public void storyList() {
        Category category = categoryService.findByCategory_key("ARTICLE_STORY");

        List<Article> articleList = articleService.listByCategory_id(category.getCategory_id());

        renderSuccessJson(articleList);
    }

    @ActionKey(Url.ARTICLE_SCIENCE_LIST)
    public void scienceList() {
        Category category = categoryService.findByCategory_key("ARTICLE_SCIENCE");

        List<Article> articleList = articleService.listByCategory_id(category.getCategory_id());

        renderSuccessJson(articleList);
    }

    @ActionKey(Url.ARTICLE_CATEGORY_LIST)
    public void categoryList() {
        List<Map<String, Object>> resultList = articleService.categoryList();

        renderSuccessJson(resultList);
    }

    @ActionKey(Url.ARTICLE_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Article model = getParameter(Article.class);

        model.validate(Article.ARTICLE_NAME);

        int count = articleService.count(model.getArticle_name());

        List<Article> articleListvice = articleService.list(model.getArticle_name(), getM(), getN());

        renderSuccessJson(count, articleListvice);
    }

    @ActionKey(Url.ARTICLE_FIND)
    public void find() {
        Article model = getParameter(Article.class);

        model.validate(Article.ARTICLE_ID);

        Article article = articleService.find(model.getArticle_id());

        renderSuccessJson(article.removeUnfindable());
    }

    @ActionKey(Url.ARTICLE_ADMIN_FIND)
    public void adminFind() {
        Article model = getParameter(Article.class);

        model.validate(Article.ARTICLE_ID);

        Article article = articleService.find(model.getArticle_id());

        renderSuccessJson(article.removeSystemInfo());
    }

    @ActionKey(Url.ARTICLE_SAVE)
    public void save() {
        Article model = getParameter(Article.class);
        String request_user_id = getRequest_user_id();

        model.validate(Article.ARTICLE_NAME);

        articleService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ARTICLE_UPDATE)
    public void update() {
        Article model = getParameter(Article.class);
        String request_user_id = getRequest_user_id();

        model.validate(Article.ARTICLE_ID, Article.ARTICLE_NAME);

        articleService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ARTICLE_DELETE)
    public void delete() {
        Article model = getParameter(Article.class);
        String request_user_id = getRequest_user_id();

        model.validate(Article.ARTICLE_ID);

        articleService.delete(model, request_user_id);

        renderSuccessJson();
    }

}