package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Scene;

public class SceneCache extends Cache {

    private final String SCENE_OBJECT_CACHE = "scene_object_cache";

    public Scene getSceneByScene_id(String scene_id) {
        return (Scene) getObjectBykeyAndId(SCENE_OBJECT_CACHE, scene_id);
    }

    public void setSceneByScene_id(Scene scene, String scene_id) {
        setObjectBykeyAndId(scene, SCENE_OBJECT_CACHE, scene_id);
    }

    public void removeSceneByScene_id(String scene_id) {
        removeObjectBykeyAndId(SCENE_OBJECT_CACHE, scene_id);
    }

    public void removeScene() {
        removeObjectByKey(SCENE_OBJECT_CACHE);
    }

}
