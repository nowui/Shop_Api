package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

import java.math.BigDecimal;

public class OrderProduct extends Model<OrderProduct> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "订单商品编号")
    public static final String ORDER_PRODUCT_ID = "order_product_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "订单编号")
    public static final String ORDER_ID = "order_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "订单状态")
    public static final String ORDER_STATUS = "order_status";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "分类编号")
    public static final String CATEGORY_ID = "category_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "分类名称")
    public static final String CATEGORY_NAME = "category_name";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "品牌编号")
    public static final String BRAND_ID = "brand_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "品牌名称")
    public static final String BRAND_NAME = "brand_name";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "商品名称")
    public static final String PRODUCT_NAME = "product_name";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "商品图片")
    public static final String PRODUCT_IMAGE = "product_image";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "商品图片")
    public static final String PRODUCT_IMAGE_LIST = "product_image_list";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否新品")
    public static final String PRODUCT_IS_NEW = "product_is_new";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否推荐")
    public static final String PRODUCT_IS_RECOMMEND = "product_is_recommend";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否特价")
    public static final String PRODUCT_IS_BARGAIN = "product_is_bargain";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否热销")
    public static final String PRODUCT_IS_HOT = "product_is_hot";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否上架")
    public static final String PRODUCT_IS_SALE = "product_is_sale";

    @Column(type = ColumnType.LONGTEXT, length = 0, comment = "商品介绍")
    public static final String PRODUCT_CONTENT = "product_content";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "SKU编号")
    public static final String SKU_ID = "sku_id";

    @Column(type = ColumnType.INT, length = 11, comment = "佣金编号")
    public static final String COMMISSION_ID = "commission_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员编号")
    public static final String MEMBER_ID = "member_id";

    @Column(type = ColumnType.INT, length = 1000, comment = "订单商品分成")
    public static final String ORDER_PRODUCT_COMMISSION = "order_product_commission";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "商品属性")
    public static final String PRODUCT_ATTRIBUTE = "product_attribute";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "市场价格")
    public static final String PRODUCT_MARKET_PRICE = "product_market_price";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "商品价格")
    public static final String PRODUCT_PRICE = "product_price";

    @Column(type = ColumnType.INT, length = 11, comment = "商品库存")
    public static final String PRODUCT_STOCK = "product_stock";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "订单商品价格")
    public static final String ORDER_PRODUCT_PRICE = "order_product_price";

    @Column(type = ColumnType.INT, length = 11, comment = "订单商品数量")
    public static final String ORDER_PRODUCT_QUANTITY = "order_product_quantity";
    
    public String getOrder_product_id() {
        return getStr(ORDER_PRODUCT_ID);
    }

    public void setOrder_product_id(String order_product_id) {
        set(ORDER_PRODUCT_ID, order_product_id);
    }

    public String getOrder_id() {
        return getStr(ORDER_ID);
    }

    public void setOrder_id(String order_id) {
        set(ORDER_ID, order_id);
    }

    public Boolean getOrder_status() {
        return getBoolean(ORDER_STATUS);
    }

    public void setOrder_status(Boolean order_status) {
        set(ORDER_STATUS, order_status);
    }

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

    public String getCategory_name() {
        return getStr(CATEGORY_NAME);
    }

    public void setCategory_name(String category_name) {
        set(CATEGORY_NAME, category_name);
    }

    public String getBrand_id() {
        return getStr(BRAND_ID);
    }

    public void setBrand_id(String brand_id) {
        set(BRAND_ID, brand_id);
    }

    public String getBrand_name() {
        return getStr(BRAND_NAME);
    }

    public void setBrand_name(String brand_name) {
        set(BRAND_NAME, brand_name);
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

    public String getSku_id() {
        return getStr(SKU_ID);
    }

    public void setSku_id(String sku_id) {
        set(SKU_ID, sku_id);
    }

    public String getCommission_id() {
        return getStr(COMMISSION_ID);
    }

    public void setCommission_id(String commission_id) {
        set(COMMISSION_ID, commission_id);
    }

    public String getMember_id() {
        return getStr(MEMBER_ID);
    }

    public void setMember_id(String member_id) {
        set(MEMBER_ID, member_id);
    }

    public String getOrder_product_commission() {
        return getStr(ORDER_PRODUCT_COMMISSION);
    }

    public void setOrder_product_commission(String order_product_commission) {
        set(ORDER_PRODUCT_COMMISSION, order_product_commission);
    }

    public String getProduct_attribute() {
        return getStr(PRODUCT_ATTRIBUTE);
    }

    public void setProduct_attribute(String product_attribute) {
        set(PRODUCT_ATTRIBUTE, product_attribute);
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

    public BigDecimal getOrder_product_price() {
        return getBigDecimal(ORDER_PRODUCT_PRICE);
    }

    public void setOrder_product_price(BigDecimal order_product_price) {
        set(ORDER_PRODUCT_PRICE, order_product_price);
    }

    public Integer getOrder_product_quantity() {
        return getInt(ORDER_PRODUCT_QUANTITY);
    }

    public void setOrder_product_quantity(Integer order_product_quantity) {
        set(ORDER_PRODUCT_QUANTITY, order_product_quantity);
    }
}