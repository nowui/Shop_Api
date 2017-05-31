package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.SceneDao;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class SceneCache extends Cache {

    private final String SCENED_BY_SCENED_ID_CACHE = "scene_by_scene_id_cache";

    private SceneDao sceneDao = new SceneDao();

    public int count(String scene_type) {
        return sceneDao.count(scene_type);
    }

    public List<Scene> list(String scene_type, Integer m, Integer n) {
        return sceneDao.list(scene_type, m, n);
    }

    public Scene find(String scene_id) {
        Scene scene = CacheUtil.get(SCENED_BY_SCENED_ID_CACHE, scene_id);

        if (scene == null) {
            scene = sceneDao.find(scene_id);

            CacheUtil.put(SCENED_BY_SCENED_ID_CACHE, scene_id, scene);
        }
        return scene;
    }

    public Scene save(String scene_id, String object_id, String scene_type, boolean scene_is_expire, String scene_qrcode, String request_user_id) {
        return sceneDao.save(scene_id, object_id, scene_type, scene_is_expire, scene_qrcode, request_user_id);
    }

    public boolean updateScene_addByScene_id(String scene_id, String request_user_id) {
        CacheUtil.remove(SCENED_BY_SCENED_ID_CACHE, scene_id);

        return sceneDao.updateScene_addByScene_id(scene_id, request_user_id);
    }

    public boolean updateScene_cancelByScene_id(String scene_id, String request_user_id) {
        CacheUtil.remove(SCENED_BY_SCENED_ID_CACHE, scene_id);

        return sceneDao.updateScene_cancelByScene_id(scene_id, request_user_id);
    }

    public boolean updateScene_is_expireByScene_id(String scene_id, String request_user_id) {
        CacheUtil.remove(SCENED_BY_SCENED_ID_CACHE, scene_id);

        return sceneDao.updateScene_is_expireByScene_id(scene_id, request_user_id);
    }

    public boolean delete(String scene_id, String request_user_id) {
        CacheUtil.remove(SCENED_BY_SCENED_ID_CACHE, scene_id);

        return sceneDao.delete(scene_id, request_user_id);
    }

}