package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class Sku extends Model<Sku> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "SKU编号")
    public static final String SKU_ID = "sku_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 1000, comment = "商品属性")
    public static final String PRODUCT_ATTRIBUTE = "product_attribute";

    @Column(type = ColumnTypeEnum.DECIMAL, length = 0, comment = "商品价格")
    public static final String PRODUCT_PRICE = "product_price";

    @Column(type = ColumnTypeEnum.INT, length = 11, comment = "商品库存")
    public static final String PRODUCT_STOCK = "product_stock";

    public static final String SKU_LIST = "sku_list";

    
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

    public Integer getProduct_stock() {
        return getInt(PRODUCT_STOCK);
    }

    public void setProduct_stock(Integer product_stock) {
        set(PRODUCT_STOCK, product_stock);
    }
}