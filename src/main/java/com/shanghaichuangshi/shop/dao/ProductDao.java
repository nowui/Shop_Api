package com.shanghaichuangshi.shop.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ProductDao extends Dao {

    private final String PRODUCT_CACHE = "product_cache";

    public int count(String product_name) {
        Kv map = Kv.create();
        map.put(Product.PRODUCT_NAME, product_name);
        SqlPara sqlPara = Db.getSqlPara("product.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Product> list(String product_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Product.PRODUCT_NAME, product_name);
        map.put(Product.M, m);
        map.put(Product.N, n);
        SqlPara sqlPara = Db.getSqlPara("product.list", map);

        return new Product().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Product> listAll() {
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("product.listAll", map);

        return new Product().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Product> listAllHot() {
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("product.listAllHot", map);

        return new Product().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Product find(String product_id) {
        Product product = CacheUtil.get(PRODUCT_CACHE, product_id);

        if (product == null) {
            Kv map = Kv.create();
            map.put(Product.PRODUCT_ID, product_id);
            SqlPara sqlPara = Db.getSqlPara("product.find", map);

            List<Product> productList = new Product().find(sqlPara.getSql(), sqlPara.getPara());
            if (productList.size() == 0) {
                product = null;
            } else {
                product = productList.get(0);

                CacheUtil.put(PRODUCT_CACHE, product_id, product);
            }
        }

        return product;
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
        CacheUtil.remove(PRODUCT_CACHE, product.getProduct_id());

        product.remove(Product.SYSTEM_CREATE_USER_ID);
        product.remove(Product.SYSTEM_CREATE_TIME);
        product.setSystem_update_user_id(request_user_id);
        product.setSystem_update_time(new Date());
        product.remove(Product.SYSTEM_STATUS);

        return product.update();
    }

    public boolean delete(String product_id, String request_user_id) {
        CacheUtil.remove(PRODUCT_CACHE, product_id);

        Kv map = Kv.create();
        map.put(Product.PRODUCT_ID, product_id);
        map.put(Product.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Product.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("product.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}