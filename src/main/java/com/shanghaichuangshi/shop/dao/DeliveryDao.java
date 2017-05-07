package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class DeliveryDao extends Dao {

    private final String DELIVERY_LIST_CACHE = "delivery_list_cache";

    public int count(String delivery_name, String user_id) {
        Kv map = Kv.create();
        map.put(Delivery.DELIVERY_NAME, delivery_name);
        map.put(Delivery.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("delivery.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Delivery> list(String delivery_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Delivery.DELIVERY_NAME, delivery_name);
        map.put(Delivery.M, m);
        map.put(Delivery.N, n);
        SqlPara sqlPara = Db.getSqlPara("delivery.list", map);

        return new Delivery().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Delivery> listByUser_id(String user_id, Integer m, Integer n) {
        List<Delivery> deliveryList = CacheUtil.get(DELIVERY_LIST_CACHE, user_id);

        if (deliveryList == null) {
            Kv map = Kv.create();
            map.put(Delivery.USER_ID, user_id);
            map.put(Delivery.M, m);
            map.put(Delivery.N, n);
            SqlPara sqlPara = Db.getSqlPara("delivery.listByUser_id", map);

            deliveryList = new Delivery().find(sqlPara.getSql(), sqlPara.getPara());

            if (deliveryList.size() == 0) {

            } else {
                CacheUtil.put(DELIVERY_LIST_CACHE, user_id, deliveryList);
            }
        }

        return deliveryList;
    }

    public Delivery find(String delivery_id) {
        Kv map = Kv.create();
        map.put(Delivery.DELIVERY_ID, delivery_id);
        SqlPara sqlPara = Db.getSqlPara("delivery.find", map);

        List<Delivery> deliveryList = new Delivery().find(sqlPara.getSql(), sqlPara.getPara());
        if (deliveryList.size() == 0) {
            return null;
        } else {
            return deliveryList.get(0);
        }
    }

    public Delivery findDefaultByUser_id(String user_id) {
        Kv map = Kv.create();
        map.put(Delivery.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("delivery.findDefaultByUser_id", map);

        List<Delivery> deliveryList = new Delivery().find(sqlPara.getSql(), sqlPara.getPara());
        if (deliveryList.size() == 0) {
            return null;
        } else {
            return deliveryList.get(0);
        }
    }

    public Delivery save(Delivery delivery, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_CACHE, request_user_id);

        delivery.setDelivery_id(Util.getRandomUUID());
        delivery.setUser_id(request_user_id);
        delivery.setSystem_create_user_id(request_user_id);
        delivery.setSystem_create_time(new Date());
        delivery.setSystem_update_user_id(request_user_id);
        delivery.setSystem_update_time(new Date());
        delivery.setSystem_status(true);

        delivery.save();

        return delivery;
    }

    public boolean update(Delivery delivery, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_CACHE, request_user_id);

        delivery.remove(Delivery.SYSTEM_CREATE_USER_ID);
        delivery.remove(Delivery.SYSTEM_CREATE_TIME);
        delivery.setSystem_update_user_id(request_user_id);
        delivery.setSystem_update_time(new Date());
        delivery.remove(Delivery.SYSTEM_STATUS);

        return delivery.update();
    }

    public boolean updateIsDefault(String delivery_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Delivery.DELIVERY_ID, delivery_id);
        map.put(Delivery.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Delivery.SYSTEM_UPDATE_TIME, new Date());
        map.put(Delivery.USER_ID, request_user_id);
        SqlPara sqlPara = Db.getSqlPara("delivery.updateIsDefault", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String delivery_id, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_CACHE, request_user_id);

        Kv map = Kv.create();
        map.put(Delivery.DELIVERY_ID, delivery_id);
        map.put(Delivery.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Delivery.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("delivery.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}