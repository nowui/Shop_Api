package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Sku extends Model<Sku> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "SKU编号")
    public static final String SKU_ID = "sku_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "商品属性")
    public static final String PRODUCT_ATTRIBUTE = "product_attribute";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "商品价格")
    public static final String PRODUCT_PRICE = "product_price";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "会员等级价格")
    public static final String MEMBER_LEVEL_PRICE = "member_level_price";

    @Column(type = ColumnType.INT, length = 11, comment = "商品库存")
    public static final String PRODUCT_STOCK = "product_stock";

    
    public String getSku_id() {
        return getStr(SKU_ID);
    }

    public void setSku_id(String sku_id) {
        set(SKU_ID, sku_id);
    }

    public String getProduct_id() {
        return getStr(PRODUCT_ID);
    }

    public void setProduct_id(String product_id) {
        set(PRODUCT_ID, product_id);
    }

    public String getProduct_attribute() {
        return getStr(PRODUCT_ATTRIBUTE);
    }

    public void setProduct_attribute(String product_attribute) {
        set(PRODUCT_ATTRIBUTE, product_attribute);
    }

    public String getProduct_price() {
        return getStr(PRODUCT_PRICE);
    }

    public void setProduct_price(String product_price) {
        set(PRODUCT_PRICE, product_price);
    }

    public String getMember_level_price() {
        return getStr(MEMBER_LEVEL_PRICE);
    }

    public void setMember_level_price(String member_level_price) {
        set(MEMBER_LEVEL_PRICE, member_level_price);
    }

    public String getProduct_stock() {
        return getStr(PRODUCT_STOCK);
    }

    public void setProduct_stock(String product_stock) {
        set(PRODUCT_STOCK, product_stock);
    }
}