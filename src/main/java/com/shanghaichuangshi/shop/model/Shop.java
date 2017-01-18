package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.annotation.Id;
import com.shanghaichuangshi.annotation.Table;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Shop extends Model<Shop> {

    @Table()
    public static final String TABLE_SHOP = "table_shop";

    @Id
    @Column(type = ColumnType.VARCHAR, length = 32, comment = "店铺编号")
    public static final String SHOP_ID = "shop_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "店铺名称")
    public static final String SHOP_NAME = "shop_name";

    public String getShop_id() {
        return getString(SHOP_ID);
    }

    public void setShop_id(String shop_id) {
        set(SHOP_ID, shop_id);
    }

    public String getShop_name() {
        return getString(SHOP_NAME);
    }

    public void setShop_name(String shop_name) {
        set(SHOP_NAME, shop_name);
    }

}