package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class ExpressDao extends Dao {

    public int count(String order_id) {
        Kv map = Kv.create();
        map.put(Express.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("express.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Express> list(String order_id, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Express.ORDER_ID, order_id);
        map.put(Express.M, m);
        map.put(Express.N, n);
        SqlPara sqlPara = Db.getSqlPara("express.list", map);

        return new Express().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Express find(String express_id) {
        Kv map = Kv.create();
        map.put(Express.EXPRESS_ID, express_id);
        SqlPara sqlPara = Db.getSqlPara("express.find", map);

        List<Express> expressList = new Express().find(sqlPara.getSql(), sqlPara.getPara());
        if (expressList.size() == 0) {
            return null;
        } else {
            return expressList.get(0);
        }
    }

    public Express save(Express express, String request_user_id) {
        express.setExpress_id(Util.getRandomUUID());
        express.setSystem_create_user_id(request_user_id);
        express.setSystem_create_time(new Date());
        express.setSystem_update_user_id(request_user_id);
        express.setSystem_update_time(new Date());
        express.setSystem_status(true);

        express.save();

        return express;
    }

    public boolean update(Express express, String request_user_id) {
        express.remove(Express.SYSTEM_CREATE_USER_ID);
        express.remove(Express.SYSTEM_CREATE_TIME);
        express.setSystem_update_user_id(request_user_id);
        express.setSystem_update_time(new Date());
        express.remove(Express.SYSTEM_STATUS);

        return express.update();
    }

    public boolean delete(String express_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Express.EXPRESS_ID, express_id);
        map.put(Express.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Express.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("express.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}