package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController {

    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppId("wx0e4f5620c0cd2966");
        apiConfig.setAppSecret("ac5d368b285820c94d7b79541d40c3b2");
        apiConfig.setToken("ShanghaiStarChannel");
        apiConfig.setEncryptMessage(false);
        apiConfig.setEncodingAesKey("4ZXxqQuBd7B1nNGLLFBVyRGMvnqQslkKbUOuTrAlhac");

        return apiConfig;
    }

    public void index() {
        ApiResult jsonResult = MenuApi.createMenu("{\"button\":[{\"name\":\"我的健康\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的积分\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的病历\",\"key\":\"V1001_TODAY_MUSIC\"}]},{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"http://h5.jiyiguan.nowui.com\"},{\"name\":\"服务中心\",\"sub_button\":[{\"type\":\"click\",\"name\":\"个人中心\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"在线咨询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"文献查询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的物流\",\"key\":\"V1001_TODAY_MUSIC\"}]}]}");

        renderText(jsonResult.getJson());
    }

}
