package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class Article extends Model<Article> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "文章编号")
    public static final String ARTICLE_ID = "article_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "分类编号")
    public static final String CATEGORY_ID = "category_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 100, comment = "文章名称")
    public static final String ARTICLE_NAME = "article_name";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "文章图片")
    public static final String ARTICLE_IMAGE = "article_image";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 250, comment = "文章摘要")
    public static final String ARTICLE_SUMMARY = "article_summary";

    @Column(type = ColumnTypeEnum.LONGTEXT, length = 0, comment = "文章内容")
    public static final String ARTICLE_CONTENT = "article_content";

    public static final String ARTICLE_IMAGE_FILE = "article_image_file";

    public String getArticle_id() {
        return getStr(ARTICLE_ID);
    }

    public void setArticle_id(String article_id) {
        set(ARTICLE_ID, article_id);
    }

    public String getCategory_id() {
        return getStr(CATEGORY_ID);
    }

    public void setCategory_id(String category_id) {
        set(CATEGORY_ID, category_id);
    }

    public String getArticle_name() {
        return getStr(ARTICLE_NAME);
    }

    public void setArticle_name(String article_name) {
        set(ARTICLE_NAME, article_name);
    }

    public String getArticle_image() {
        return getStr(ARTICLE_IMAGE);
    }

    public void setArticle_image(String article_image) {
        set(ARTICLE_IMAGE, article_image);
    }

    public String getArticle_summary() {
        return getStr(ARTICLE_SUMMARY);
    }

    public void setArticle_summary(String article_summary) {
        set(ARTICLE_SUMMARY, article_summary);
    }

    public String getArticle_content() {
        return getStr(ARTICLE_CONTENT);
    }

    public void setArticle_content(String article_content) {
        set(ARTICLE_CONTENT, article_content);
    }
}