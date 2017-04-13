package com.shanghaichuangshi.shop.service;

import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.shop.dao.SceneDao;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.util.Util;

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

    public Scene save(String scene_id, String object_id, String scene_type, boolean scene_is_temporary, String scene_qrcode, String request_user_id) {
        return sceneDao.save(scene_id, object_id, scene_type, scene_is_temporary, scene_qrcode, request_user_id);
    }

    public Scene companySave(String request_user_id) {
        String scene_id = Util.getRandomUUID();
        String object_id = "";
        String scene_type = SceneTypeEnum.COMPANY.getKey();
        Boolean scene_is_temporary = false;

        ApiConfigKit.setThreadLocalApiConfig(WeChat.getApiConfig());
        ApiResult apiResult = QrcodeApi.createPermanent(scene_id);
        String scene_qrcode = QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket"));

        return save(scene_id, object_id, scene_type, scene_is_temporary, scene_qrcode, request_user_id);
    }

//    public boolean update(Scene scene, String request_user_id) {
//        return sceneDao.update(scene, request_user_id);
//    }

    public boolean updateScene_addByScene_id(String scene_id, String request_user_id) {
        return sceneDao.updateScene_addByScene_id(scene_id, request_user_id);
    }

    public boolean updateScene_cancelByScene_id(String scene_id, String request_user_id) {
        return sceneDao.updateScene_cancelByScene_id(scene_id, request_user_id);
    }

    public boolean delete(Scene scene, String request_user_id) {
        return sceneDao.delete(scene.getScene_id(), request_user_id);
    }

}