package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Fute;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class FuteDao extends Dao {

    public int count(String fute_name) {
        JMap map = JMap.create();
        map.put(Fute.FUTE_NAME, fute_name);
        SqlPara sqlPara = Db.getSqlPara("fute.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Fute> list(String fute_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Fute.FUTE_NAME, fute_name);
        map.put(Fute.M, m);
        map.put(Fute.N, n);
        SqlPara sqlPara = Db.getSqlPara("fute.list", map);

        return new Fute().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Fute find(String fute_id) {
        JMap map = JMap.create();
        map.put(Fute.FUTE_ID, fute_id);
        SqlPara sqlPara = Db.getSqlPara("fute.find", map);

        List<Fute> futeList = new Fute().find(sqlPara.getSql(), sqlPara.getPara());
        if (futeList.size() == 0) {
            return null;
        } else {
            return futeList.get(0);
        }
    }

    public Fute save(Fute fute, String request_user_id) {
        fute.setFute_id(Util.getRandomUUID());
        fute.setSystem_create_user_id(request_user_id);
        fute.setSystem_create_time(new Date());
        fute.setSystem_update_user_id(request_user_id);
        fute.setSystem_update_time(new Date());
        fute.setSystem_status(true);

        fute.save();

        return fute;
    }

    public boolean update(Fute fute, String request_user_id) {
        fute.remove(Fute.SYSTEM_CREATE_USER_ID);
        fute.remove(Fute.SYSTEM_CREATE_TIME);
        fute.setSystem_update_user_id(request_user_id);
        fute.setSystem_update_time(new Date());
        fute.remove(Fute.SYSTEM_STATUS);

        return fute.update();
    }

    public boolean delete(String fute_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Fute.FUTE_ID, fute_id);
        map.put(Fute.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Fute.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("fute.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}