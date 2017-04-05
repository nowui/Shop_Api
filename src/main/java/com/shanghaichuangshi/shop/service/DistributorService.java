package com.shanghaichuangshi.shop.service;

import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.dao.DistributorDao;
import com.shanghaichuangshi.shop.model.Distributor;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class DistributorService extends Service {

    private final DistributorDao distributorDao = new DistributorDao();

    private final UserService userService = new UserService();
    private final SceneService sceneService = new SceneService();

    public int count(Distributor distributor) {
        return distributorDao.count(distributor.getDistributor_name());
    }

    public List<Distributor> list(Distributor distributor, int m, int n) {
        return distributorDao.list(distributor.getDistributor_name(), m, n);
    }

    public Distributor find(String distributor_id) {
        return distributorDao.find(distributor_id);
    }

    public Distributor save(Distributor distributor, User user, String request_user_id) {
        String scene_id = Util.getRandomUUID();
        String distributor_id = Util.getRandomUUID();
        String user_id = Util.getRandomUUID();

        ApiConfigKit.setThreadLocalApiConfig(WeChat.getApiConfig());
        ApiResult apiResult = QrcodeApi.createPermanent(scene_id);
        String scene_qrcode = QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket"));

        sceneService.save(scene_id, distributor_id, SceneTypeEnum.DISTRIBUTOR.getKey(), scene_qrcode, request_user_id);

        distributor.setUser_id(user_id);
        distributor.setScene_id(scene_id);
        distributor.setScene_qrcode(scene_qrcode);

        distributorDao.save(distributor_id, distributor, request_user_id);

        userService.saveByUser_idAndUser_accountAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_account(), user.getUser_password(), distributor_id, UserType.DISTRIBUTOR.getKey(), request_user_id);

        return distributor;
    }

    public boolean update(Distributor distributor, User user, String request_user_id) {
        boolean result = distributorDao.update(distributor, request_user_id);

        userService.updateByObject_idAndUser_accountAndUser_type(distributor.getDistributor_id(), user.getUser_account(), UserType.DISTRIBUTOR.getKey(), request_user_id);

        userService.updateByObject_idAndUser_passwordAndUser_type(distributor.getDistributor_id(), user.getUser_password(), UserType.DISTRIBUTOR.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Distributor distributor, String request_user_id) {
        boolean result = distributorDao.delete(distributor.getDistributor_id(), request_user_id);

        userService.deleteByObject_idAndUser_type(distributor.getDistributor_id(), UserType.DISTRIBUTOR.getKey(), request_user_id);

        return result;
    }

}