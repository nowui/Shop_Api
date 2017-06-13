package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Article;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ArticleDao extends Dao {

    public int count(String article_name) {
        Kv map = Kv.create();
        map.put(Article.ARTICLE_NAME, article_name);
        SqlPara sqlPara = Db.getSqlPara("article.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Article> list(String article_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Article.ARTICLE_NAME, article_name);
        map.put(Article.M, m);
        map.put(Article.N, n);
        SqlPara sqlPara = Db.getSqlPara("article.list", map);

        return new Article().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Article> listByCategory_id(String category_id) {
        Kv map = Kv.create();
        map.put(Article.CATEGORY_ID, category_id);
        SqlPara sqlPara = Db.getSqlPara("article.listByCategory_id", map);

        return new Article().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Article find(String article_id) {
        Kv map = Kv.create();
        map.put(Article.ARTICLE_ID, article_id);
        SqlPara sqlPara = Db.getSqlPara("article.find", map);

        List<Article> articleList = new Article().find(sqlPara.getSql(), sqlPara.getPara());
        if (articleList.size() == 0) {
            return null;
        } else {
            return articleList.get(0);
        }
    }

    public Article save(Article article, String request_user_id) {
        article.setArticle_id(Util.getRandomUUID());
        article.setSystem_create_user_id(request_user_id);
        article.setSystem_create_time(new Date());
        article.setSystem_update_user_id(request_user_id);
        article.setSystem_update_time(new Date());
        article.setSystem_status(true);

        article.save();

        return article;
    }

    public boolean update(Article article, String request_user_id) {
        article.remove(Article.SYSTEM_CREATE_USER_ID);
        article.remove(Article.SYSTEM_CREATE_TIME);
        article.setSystem_update_user_id(request_user_id);
        article.setSystem_update_time(new Date());
        article.remove(Article.SYSTEM_STATUS);

        return article.update();
    }

    public boolean delete(String article_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Article.ARTICLE_ID, article_id);
        map.put(Article.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Article.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("article.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}