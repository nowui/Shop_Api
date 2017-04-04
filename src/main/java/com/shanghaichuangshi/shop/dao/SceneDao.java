package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class SceneDao extends Dao {

    public int count(String scene_type) {
        JMap map = JMap.create();
        map.put(Scene.SCENE_TYPE, scene_type);
        SqlPara sqlPara = Db.getSqlPara("scene.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Scene> list(String scene_type, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Scene.SCENE_TYPE, scene_type);
        map.put(Scene.M, m);
        map.put(Scene.N, n);
        SqlPara sqlPara = Db.getSqlPara("scene.list", map);

        return new Scene().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Scene find(String scene_id) {
        JMap map = JMap.create();
        map.put(Scene.SCENE_ID, scene_id);
        SqlPara sqlPara = Db.getSqlPara("scene.find", map);

        List<Scene> sceneList = new Scene().find(sqlPara.getSql(), sqlPara.getPara());
        if (sceneList.size() == 0) {
            return null;
        } else {
            return sceneList.get(0);
        }
    }

    public Scene save(String scene_id, String object_id, String scene_type, String scene_qrcode, String request_user_id) {
        Scene scene = new Scene();
        scene.setScene_id(scene_id);
        scene.setObject_id(object_id);
        scene.setScene_type(scene_type);
        scene.setScene_add(0);
        scene.setScene_cancel(0);
        scene.setScene_qrcode(scene_qrcode);
        scene.setSystem_create_user_id(request_user_id);
        scene.setSystem_create_time(new Date());
        scene.setSystem_update_user_id(request_user_id);
        scene.setSystem_update_time(new Date());
        scene.setSystem_status(true);

        scene.save();

        return scene;
    }

    public boolean update(Scene scene, String request_user_id) {
        scene.remove(Scene.SYSTEM_CREATE_USER_ID);
        scene.remove(Scene.SYSTEM_CREATE_TIME);
        scene.setSystem_update_user_id(request_user_id);
        scene.setSystem_update_time(new Date());
        scene.remove(Scene.SYSTEM_STATUS);

        return scene.update();
    }

    public boolean delete(String scene_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Scene.SCENE_ID, scene_id);
        map.put(Scene.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Scene.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("scene.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}