package com.shanghaichuangshi.shop.dao;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.util.Util;

import java.util.*;

public class ExpressDao extends Dao {

    public int count(String express_number) {
        Kv map = Kv.create();
        map.put(Express.EXPRESS_NUMBER, express_number);
        SqlPara sqlPara = Db.getSqlPara("express.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Express> list(String express_number, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Express.EXPRESS_NUMBER, express_number);
        map.put(Express.M, m);
        map.put(Express.N, n);
        SqlPara sqlPara = Db.getSqlPara("express.list", map);

        return new Express().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Express> listByOrder_id(String order_id) {
        Kv map = Kv.create();
        map.put(Express.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("express.listByOrder_id", map);

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

    public Express save(String express_id, Express express, String request_user_id) {
        express.setExpress_id(express_id);
        express.setExpress_flow("");
        express.setExpress_status(true);
        express.setExpress_trace(new JSONArray().toJSONString());
        express.setSystem_create_user_id(request_user_id);
        express.setSystem_create_time(new Date());
        express.setSystem_update_user_id(request_user_id);
        express.setSystem_update_time(new Date());
        express.setSystem_status(true);

        express.save();

        return express;
    }

    public boolean update(Express express, String request_user_id) {
        express.remove(Express.ORDER_ID);
        express.remove(Express.SYSTEM_CREATE_USER_ID);
        express.remove(Express.SYSTEM_CREATE_TIME);
        express.setSystem_update_user_id(request_user_id);
        express.setSystem_update_time(new Date());
        express.remove(Express.SYSTEM_STATUS);

        return express.update();
    }

    public Boolean updateBusiness(List<Express> expressList) {
        if (expressList.size() == 0) {
            return true;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("express.updateBusiness", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Express express : expressList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(express.getExpress_flow());
            objectList.add(express.getExpress_status());
            objectList.add(express.getExpress_trace());
            objectList.add(new Date());
            objectList.add(express.getExpress_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                return false;
            }
        }

        return true;
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