package com.shanghaichuangshi.shop.dao;

import com.shanghaichuangshi.config.DynamicSQL;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Shop;
import com.shanghaichuangshi.util.DatabaseUtil;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class ShopDao extends Dao {

    public int count() {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT COUNT(*) FROM ").append(Shop.TABLE_SHOP).append(" ");
        dynamicSQL.append("WHERE ").append(Shop.TABLE_SHOP).append(".").append(Shop.SYSTEM_STATUS).append(" = ? ", true);

        return DatabaseUtil.count(dynamicSQL.getSql(), dynamicSQL.getParameterList());
    }

    public List<Shop> list(String shop_name, Integer m, Integer n) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Shop.TABLE_SHOP).append(".").append(Shop.SHOP_ID).append(", ");
        dynamicSQL.append(Shop.TABLE_SHOP).append(".").append(Shop.SHOP_NAME).append(" ");
        dynamicSQL.append("FROM ").append(Shop.TABLE_SHOP).append(" ");
        dynamicSQL.append("WHERE ").append(Shop.TABLE_SHOP).append(".").append(Shop.SYSTEM_STATUS).append(" = ? ", true);
        if (!Util.isNullOrEmpty(shop_name)) {
            dynamicSQL.append("AND ").append(Shop.TABLE_SHOP).append(".").append(Shop.SHOP_NAME).append(" LIKE ? ", "%" + shop_name + "%");
        }
        dynamicSQL.append("ORDER BY ").append(Shop.TABLE_SHOP).append(".").append(Shop.SYSTEM_CREATE_TIME).append(" DESC ");
        if (n > 0) {
            dynamicSQL.append("LIMIT ?, ? ", m, n);
        }

        return (List<Shop>) DatabaseUtil.list(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Shop.class);
    }

    public Shop find(String shop_id) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Shop.TABLE_SHOP).append(".* ");
        dynamicSQL.append("FROM ").append(Shop.TABLE_SHOP).append(" ");
        dynamicSQL.append("WHERE ").append(Shop.TABLE_SHOP).append(".").append(Shop.SYSTEM_STATUS).append(" = ? ", true);
        dynamicSQL.append("AND ").append(Shop.TABLE_SHOP).append(".").append(Shop.SHOP_ID).append(" = ? ", shop_id);

        return (Shop) DatabaseUtil.find(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Shop.class);
    }

    public boolean save(Shop shop) {
        shop.setShop_id(Util.getRandomUUID());

        return shop.save();
    }

    public boolean update(Shop shop) {
        return shop.update();
    }

    public boolean delete(Shop shop) {
        return shop.delete();
    }

}