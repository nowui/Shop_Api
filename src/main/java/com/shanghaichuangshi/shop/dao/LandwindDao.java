package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Landwind;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class LandwindDao extends Dao {

    public int count(String landwind_name) {
        JMap map = JMap.create();
        map.put(Landwind.LANDWIND_NAME, landwind_name);
        SqlPara sqlPara = Db.getSqlPara("landwind.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Landwind> list(String landwind_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Landwind.LANDWIND_NAME, landwind_name);
        map.put(Landwind.M, m);
        map.put(Landwind.N, n);
        SqlPara sqlPara = Db.getSqlPara("landwind.list", map);

        return new Landwind().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Landwind find(String landwind_id) {
        JMap map = JMap.create();
        map.put(Landwind.LANDWIND_ID, landwind_id);
        SqlPara sqlPara = Db.getSqlPara("landwind.find", map);

        List<Landwind> landwindList = new Landwind().find(sqlPara.getSql(), sqlPara.getPara());
        if (landwindList.size() == 0) {
            return null;
        } else {
            return landwindList.get(0);
        }
    }

    public Landwind save(Landwind landwind, String request_user_id) {
        landwind.setLandwind_id(Util.getRandomUUID());
        landwind.setSystem_create_user_id(request_user_id);
        landwind.setSystem_create_time(new Date());
        landwind.setSystem_update_user_id(request_user_id);
        landwind.setSystem_update_time(new Date());
        landwind.setSystem_status(true);

        landwind.save();

        return landwind;
    }

    public boolean update(Landwind landwind, String request_user_id) {
        landwind.remove(Landwind.SYSTEM_CREATE_USER_ID);
        landwind.remove(Landwind.SYSTEM_CREATE_TIME);
        landwind.setSystem_update_user_id(request_user_id);
        landwind.setSystem_update_time(new Date());
        landwind.remove(Landwind.SYSTEM_STATUS);

        return landwind.update();
    }

    public boolean delete(String landwind_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Landwind.LANDWIND_ID, landwind_id);
        map.put(Landwind.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Landwind.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("landwind.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}