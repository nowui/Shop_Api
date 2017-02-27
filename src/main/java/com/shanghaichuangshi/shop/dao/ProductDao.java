package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ProductDao extends Dao {

    public int count(String product_name) {
        JMap map = JMap.create();
        map.put(Product.PRODUCT_NAME, product_name);
        SqlPara sqlPara = Db.getSqlPara("product.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Product> list(String product_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Product.PRODUCT_NAME, product_name);
        map.put(Authorization.M, m);
        map.put(Authorization.N, n);
        SqlPara sqlPara = Db.getSqlPara("product.list", map);

        return new Product().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Product find(String product_id) {
        JMap map = JMap.create();
        map.put(Product.PRODUCT_ID, product_id);
        SqlPara sqlPara = Db.getSqlPara("product.find", map);

        List<Product> productList = new Product().find(sqlPara.getSql(), sqlPara.getPara());
        if (productList.size() == 0) {
            return null;
        } else {
            return productList.get(0);
        }
    }

    public Product save(Product product, String request_user_id) {
        product.setProduct_id(Util.getRandomUUID());
        product.setSystem_create_user_id(request_user_id);
        product.setSystem_create_time(new Date());
        product.setSystem_update_user_id(request_user_id);
        product.setSystem_update_time(new Date());
        product.setSystem_status(true);

        product.save();

        return product;
    }

    public boolean update(Product product, String request_user_id) {
        product.remove(Product.SYSTEM_CREATE_USER_ID);
        product.remove(Product.SYSTEM_CREATE_TIME);
        product.setSystem_update_user_id(request_user_id);
        product.setSystem_update_time(new Date());
        product.remove(Product.SYSTEM_STATUS);

        return product.update();
    }

    public boolean delete(String product_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Product.PRODUCT_ID, product_id);
        map.put(Product.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Product.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("product.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}