package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.cache.FileCache;
import com.shanghaichuangshi.shop.cache.ArticleCache;
import com.shanghaichuangshi.shop.model.Article;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.CategoryTypeEnum;
import com.shanghaichuangshi.util.Util;

import java.util.List;
import java.util.Map;

public class ArticleService extends Service {

    private final ArticleCache articleCache = new ArticleCache();

    private final CategoryService categoryService = new CategoryService();
    private final FileCache fileCache = new FileCache();

    public int count(String article_name) {
        return articleCache.count(article_name);
    }

    public List<Article> list(String article_name, int m, int n) {
        return articleCache.list(article_name, m, n);
    }

    public List<Article> listByCategory_id(String category_id) {
        List<Article> articleList = articleCache.listByCategory_id(category_id);

        for (Article article : articleList) {
            if (Util.isNullOrEmpty(article.getArticle_image())) {
                article.put(Article.ARTICLE_IMAGE_FILE, "");
                article.remove(Article.ARTICLE_IMAGE);
            } else {
                File file = fileCache.find(article.getArticle_image());
                article.put(Article.ARTICLE_IMAGE_FILE, file.getFile_original_path());
                article.remove(Article.ARTICLE_IMAGE);
            }
        }

        return articleList;
    }

    public List<Map<String, Object>> categoryList() {
        return categoryService.listByCategory_key(CategoryTypeEnum.ARTICLE.getKey());
    }

    public Article find(String article_id) {
        Article article = articleCache.find(article_id);

        if (Util.isNullOrEmpty(article.getArticle_image())) {
            article.put(Article.ARTICLE_IMAGE_FILE, "");
        } else {
            File productImageFile = fileCache.find(article.getArticle_image());
            article.put(Article.ARTICLE_IMAGE_FILE, productImageFile.keep(File.FILE_ID, File.FILE_NAME, File.FILE_PATH));
        }

        return article;
    }

    public Article save(Article article, String request_user_id) {
        return articleCache.save(article, request_user_id);
    }

    public boolean update(Article article, String request_user_id) {
        return articleCache.update(article, request_user_id);
    }

    public boolean delete(Article article, String request_user_id) {
        return articleCache.delete(article.getArticle_id(), request_user_id);
    }

}