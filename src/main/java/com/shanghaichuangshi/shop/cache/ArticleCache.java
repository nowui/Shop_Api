package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.ArticleDao;
import com.shanghaichuangshi.shop.model.Article;

import java.util.List;

public class ArticleCache extends Cache {

    private final ArticleDao articleDao = new ArticleDao();

    public int count(String article_name) {
        return articleDao.count(article_name);
    }

    public List<Article> list(String article_name, int m, int n) {
        return articleDao.list(article_name, m, n);
    }

    public List<Article> listByCategory_id(String category_id) {
        return articleDao.listByCategory_id(category_id);
    }

    public Article find(String article_id) {
        return articleDao.find(article_id);
    }

    public Article save(Article article, String request_user_id) {
        return articleDao.save(article, request_user_id);
    }

    public boolean update(Article article, String request_user_id) {
        return articleDao.update(article, request_user_id);
    }

    public boolean delete(String article_id, String request_user_id) {
        return articleDao.delete(article_id, request_user_id);
    }

}