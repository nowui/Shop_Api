package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.SceneDao;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class SceneService extends Service {

    private final SceneDao sceneDao = new SceneDao();

    public int count(Scene scene) {
        return sceneDao.count(scene.getScene_type());
    }

    public List<Scene> list(Scene scene, int m, int n) {
        return sceneDao.list(scene.getScene_type(), m, n);
    }

    public Scene find(String scene_id) {
        return sceneDao.find(scene_id);
    }

    public Scene save(String scene_id, String object_id, String scene_type, String scene_qrcode, String request_user_id) {
        return sceneDao.save(scene_id, object_id, scene_type, scene_qrcode, request_user_id);
    }

    public boolean update(Scene scene, String request_user_id) {
        return sceneDao.update(scene, request_user_id);
    }

    public boolean delete(Scene scene, String request_user_id) {
        return sceneDao.delete(scene.getScene_id(), request_user_id);
    }

}