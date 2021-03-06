package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

import java.math.BigDecimal;

public class Product extends Model<Product> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "分类编号", findable = false)
    public static final String CATEGORY_ID = "category_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "品牌编号", findable = false)
    public static final String BRAND_ID = "brand_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 100, comment = "商品名称")
    public static final String PRODUCT_NAME = "product_name";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "商品图片", findable = false)
    public static final String PRODUCT_IMAGE = "product_image";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 1000, comment = "商品图片", findable = false)
    public static final String PRODUCT_IMAGE_LIST = "product_image_list";

    @Column(type = ColumnTypeEnum.DECIMAL, length = 0, comment = "市场价格")
    public static final String PRODUCT_MARKET_PRICE = "product_market_price";

    @Column(type = ColumnTypeEnum.DECIMAL, length = 0, comment = "商品价格", findable = false)
    public static final String PRODUCT_PRICE = "product_price";

    @Column(type = ColumnTypeEnum.INT, length = 11, comment = "商品库存", findable = false)
    public static final String PRODUCT_STOCK = "product_stock";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "是否新品", findable = false)
    public static final String PRODUCT_IS_NEW = "product_is_new";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "是否推荐", findable = false)
    public static final String PRODUCT_IS_RECOMMEND = "product_is_recommend";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "是否特价", findable = false)
    public static final String PRODUCT_IS_BARGAIN = "product_is_bargain";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "是否热销", findable = false)
    public static final String PRODUCT_IS_HOT = "product_is_hot";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "是否上架", findable = false)
    public static final String PRODUCT_IS_SALE = "product_is_sale";

    @Column(type = ColumnTypeEnum.LONGTEXT, length = 0, comment = "商品介绍")
    public static final String PRODUCT_CONTENT = "product_content";

    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_IS_PAY = "product_is_pay";
    public static final String PRODUCT_LIST = "product_list";
    public static final String PRODUCT_IMAGE_FILE = "product_image_file";
    public static final String PRODUCT_IMAGE_FILE_LIST = "product_image_file_list";
    public static final String PRODUCT_QUANTITY_MIN = "product_quantity_min";
    public static final String EXPRESS_LIST = "express_list";

    
    public String getProduct_id() {
        return getStr(PRODUCT_ID);
    }

    public void setProduct_id(String product_id) {
        set(PRODUCT_ID, product_id);
    }

    public String getCategory_id() {
        return getStr(CATEGORY_ID);
    }

    public void setCategory_id(String category_id) {
        set(CATEGORY_ID, category_id);
    }

    public String getBrand_id() {
        return getStr(BRAND_ID);
    }

    public void setBrand_id(String brand_id) {
        set(BRAND_ID, brand_id);
    }

    public String getProduct_name() {
        return getStr(PRODUCT_NAME);
    }

    public void setProduct_name(String product_name) {
        set(PRODUCT_NAME, product_name);
    }

    public String getProduct_image() {
        return getStr(PRODUCT_IMAGE);
    }

    public void setProduct_image(String product_image) {
        set(PRODUCT_IMAGE, product_image);
    }

    public String getProduct_image_list() {
        return getStr(PRODUCT_IMAGE_LIST);
    }

    public void setProduct_image_list(String product_image_list) {
        set(PRODUCT_IMAGE_LIST, product_image_list);
    }

    public BigDecimal getProduct_market_price() {
        return getBigDecimal(PRODUCT_MARKET_PRICE);
    }

    public void setProduct_market_price(BigDecimal product_market_price) {
        set(PRODUCT_MARKET_PRICE, product_market_price);
    }

    public BigDecimal getProduct_price() {
        return getBigDecimal(PRODUCT_PRICE);
    }

    public void setProduct_price(BigDecimal product_price) {
        set(PRODUCT_PRICE, product_price);
    }

    public Integer getProduct_stock() {
        return getInt(PRODUCT_STOCK);
    }

    public void setProduct_stock(Integer product_stock) {
        set(PRODUCT_STOCK, product_stock);
    }

    public Boolean getProduct_is_new() {
        return getBoolean(PRODUCT_IS_NEW);
    }

    public void setProduct_is_new(Boolean product_is_new) {
        set(PRODUCT_IS_NEW, product_is_new);
    }

    public Boolean getProduct_is_recommend() {
        return getBoolean(PRODUCT_IS_RECOMMEND);
    }

    public void setProduct_is_recommend(Boolean product_is_recommend) {
        set(PRODUCT_IS_RECOMMEND, product_is_recommend);
    }

    public Boolean getProduct_is_bargain() {
        return getBoolean(PRODUCT_IS_BARGAIN);
    }

    public void setProduct_is_bargain(Boolean product_is_bargain) {
        set(PRODUCT_IS_BARGAIN, product_is_bargain);
    }

    public Boolean getProduct_is_hot() {
        return getBoolean(PRODUCT_IS_HOT);
    }

    public void setProduct_is_hot(Boolean product_is_hot) {
        set(PRODUCT_IS_HOT, product_is_hot);
    }

    public Boolean getProduct_is_sale() {
        return getBoolean(PRODUCT_IS_SALE);
    }

    public void setProduct_is_sale(Boolean product_is_sale) {
        set(PRODUCT_IS_SALE, product_is_sale);
    }

    public String getProduct_content() {
        return getStr(PRODUCT_CONTENT);
    }

    public void setProduct_content(String product_content) {
        set(PRODUCT_CONTENT, product_content);
    }
}