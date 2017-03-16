package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.shop.constant.Url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WeChatApiController extends ApiController {

    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppId(WeChat.app_id);
        apiConfig.setAppSecret(WeChat.app_secret);
        apiConfig.setToken(WeChat.token);
        apiConfig.setEncryptMessage(false);
        apiConfig.setEncodingAesKey(WeChat.rncoding_aes_key);

        return apiConfig;
    }


    @ActionKey(Url.WECHAT_API_MENU)
    public void menu() {
        String url = null;
        try {
            url = URLEncoder.encode("http://api.jiyiguan.nowui.com/wechat/api/auth", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        ApiResult jsonResult = MenuApi.createMenu("{\"button\":[{\"name\":\"我的健康\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的积分\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的病历\",\"key\":\"V1001_TODAY_MUSIC\"}]},{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"http://h5.jiyiguan.nowui.com\"},{\"name\":\"服务中心\",\"sub_button\":[{\"type\":\"click\",\"name\":\"个人中心\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"在线咨询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"文献查询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的物流\",\"key\":\"V1001_TODAY_MUSIC\"}]}]}");
        ApiResult jsonResult = MenuApi.createMenu("{\"button\":[{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx26c8db6f1987e4e0&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"}]}");

        renderText(jsonResult.getJson());
    }

    @ActionKey(Url.WECHAT_API_AUTH)
    public void auth() {
        String code = getPara("code");

        SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(WeChat.app_id, WeChat.app_secret, code);

        String wechat_open_id = snsAccessToken.getOpenid();

        redirect("http://h5.jiyiguan.nowui.com/#/auth/" + wechat_open_id);

//        ApiResult apiResult = UserApi.getUserInfo(snsAccessToken.getOpenid());
//
//        System.out.println(apiResult.getJson());

//        renderText(snsAccessToken.getOpenid());
    }

    @ActionKey(Url.WECHAT_API_ORCODE)
    public void orcode() {
        ApiResult apiResult = QrcodeApi.createPermanent("100");

        renderText(apiResult.getJson());
    }

}
