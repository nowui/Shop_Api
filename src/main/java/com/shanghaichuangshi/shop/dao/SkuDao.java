package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class SkuDao extends Dao {

    public List<Sku> list(String product_id) {
        JMap map = JMap.create();
        map.put(Sku.PRODUCT_ID, product_id);
        SqlPara sqlPara = Db.getSqlPara("sku.list", map);

        return new Sku().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Sku find(String sku_id) {
        JMap map = JMap.create();
        map.put(Sku.SKU_ID, sku_id);
        SqlPara sqlPara = Db.getSqlPara("sku.find", map);

        List<Sku> skuList = new Sku().find(sqlPara.getSql(), sqlPara.getPara());
        if (skuList.size() == 0) {
            return null;
        } else {
            return skuList.get(0);
        }
    }

    public Sku save(Sku sku, String request_user_id) {
        sku.setSku_id(Util.getRandomUUID());
        sku.setSystem_create_user_id(request_user_id);
        sku.setSystem_create_time(new Date());
        sku.setSystem_update_user_id(request_user_id);
        sku.setSystem_update_time(new Date());
        sku.setSystem_status(true);

        sku.save();

        return sku;
    }

    public boolean update(Sku sku, String request_user_id) {
        sku.remove(Sku.SYSTEM_CREATE_USER_ID);
        sku.remove(Sku.SYSTEM_CREATE_TIME);
        sku.setSystem_update_user_id(request_user_id);
        sku.setSystem_update_time(new Date());
        sku.remove(Sku.SYSTEM_STATUS);

        return sku.update();
    }

    public boolean delete(String sku_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Sku.SKU_ID, sku_id);
        map.put(Sku.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Sku.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("sku.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}