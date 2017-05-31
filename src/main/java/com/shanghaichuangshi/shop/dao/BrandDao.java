package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class BrandDao extends Dao {

    public int count(String brand_name) {
        Kv map = Kv.create();
        map.put(Brand.BRAND_NAME, brand_name);
        SqlPara sqlPara = Db.getSqlPara("brand.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Brand> list(String brand_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Brand.BRAND_NAME, brand_name);
        map.put(Brand.M, m);
        map.put(Brand.N, n);
        SqlPara sqlPara = Db.getSqlPara("brand.list", map);

        return new Brand().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Brand find(String brand_id) {
        Kv map = Kv.create();
        map.put(Brand.BRAND_ID, brand_id);
        SqlPara sqlPara = Db.getSqlPara("brand.find", map);

        List<Brand> brandList = new Brand().find(sqlPara.getSql(), sqlPara.getPara());
        if (brandList.size() == 0) {
            return null;
        } else {
            return brandList.get(0);
        }
    }

    public Brand save(Brand brand, String request_user_id) {
        brand.setBrand_id(Util.getRandomUUID());
        brand.setSystem_create_user_id(request_user_id);
        brand.setSystem_create_time(new Date());
        brand.setSystem_update_user_id(request_user_id);
        brand.setSystem_update_time(new Date());
        brand.setSystem_status(true);

        brand.save();

        return brand;
    }

    public boolean update(Brand brand, String request_user_id) {
        brand.remove(Brand.SYSTEM_CREATE_USER_ID);
        brand.remove(Brand.SYSTEM_CREATE_TIME);
        brand.setSystem_update_user_id(request_user_id);
        brand.setSystem_update_time(new Date());
        brand.remove(Brand.SYSTEM_STATUS);

        return brand.update();
    }

    public boolean delete(String brand_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Brand.BRAND_ID, brand_id);
        map.put(Brand.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Brand.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("brand.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}