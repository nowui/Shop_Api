package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class Cart extends Model<Cart> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "购物车编号")
    public static final String CART_ID = "cart_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "SKU编号")
    public static final String SKU_ID = "sku_id";

    @Column(type = ColumnTypeEnum.INT, length = 11, comment = "购物车商品数量")
    public static final String CART_PRODUCT_QUANTITY = "cart_product_quantity";

    public String getCart_id() {
        return getStr(CART_ID);
    }

    public void setCart_id(String cart_id) {
        set(CART_ID, cart_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getSku_id() {
        return getStr(SKU_ID);
    }

    public void setSku_id(String sku_id) {
        set(SKU_ID, sku_id);
    }

    public String getCart_product_quantity() {
        return getStr(CART_PRODUCT_QUANTITY);
    }

    public void setCart_product_quantity(String cart_product_quantity) {
        set(CART_PRODUCT_QUANTITY, cart_product_quantity);
    }
}