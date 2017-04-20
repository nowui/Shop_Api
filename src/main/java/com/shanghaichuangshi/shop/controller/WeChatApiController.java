package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.service.OrderProductService;
import com.shanghaichuangshi.shop.service.OrderService;
import com.shanghaichuangshi.shop.type.OrderStatusEnum;
import com.shanghaichuangshi.shop.type.PayTypeEnum;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WeChatApiController extends ApiController {

    private final OrderService orderService = new OrderService();
    private final OrderProductService orderProductService = new OrderProductService();

    public ApiConfig getApiConfig() {
        return WeChat.getApiConfig();
    }

    @ActionKey(Url.WECHAT_SHARE)
    public void share() {
        JsTicket jsTicket = JsTicketApi.getTicket(JsTicketApi.JsApiType.jsapi);
        String jsapi_ticket = jsTicket.getTicket();

        String url = getPara("url");

        Map<String, String> map = sign(jsapi_ticket, url);

        renderJson(map);
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    @ActionKey(Url.WECHAT_API_NOTIFY)
    public void notifyUrl() {
        String result = HttpKit.readData(getRequest());

        Map<String, String> map = PaymentKit.xmlToMap(result);

        String appid = (String) map.get("appid");
        String bank_type = (String) map.get("bank_type");
        String cash_fee = (String) map.get("cash_fee");
        String fee_type = (String) map.get("fee_type");
        String is_subscribe = (String) map.get("is_subscribe");
        String mch_id = (String) map.get("mch_id");
        String nonce_str = (String) map.get("nonce_str");
        String openid = (String) map.get("openid");
        String out_trade_no = (String) map.get("out_trade_no");
        String result_code = (String) map.get("result_code");
        String return_code = (String) map.get("return_code");
        String sign = (String) map.get("sign");
        String time_end = (String) map.get("time_end");
        String total_fee = (String) map.get("total_fee");
        String trade_type = (String) map.get("trade_type");
        String transaction_id = (String) map.get("transaction_id");

        SortedMap<String, String> parameter = new TreeMap<String, String>();
        parameter.put("appid", appid);
        parameter.put("bank_type", bank_type);
        parameter.put("cash_fee", cash_fee);
        parameter.put("fee_type", fee_type);
        parameter.put("is_subscribe", is_subscribe);
        parameter.put("mch_id", mch_id);
        parameter.put("nonce_str", nonce_str);
        parameter.put("openid", openid);
        parameter.put("out_trade_no", out_trade_no);
        parameter.put("result_code", result_code);
        parameter.put("return_code", return_code);
        parameter.put("time_end", time_end);
        parameter.put("total_fee", total_fee);
        parameter.put("trade_type", trade_type);
        parameter.put("transaction_id", transaction_id);

        String endsign = PaymentKit.createSign(parameter, WeChat.mch_key);

        if (sign.equals(endsign)) {
            BigDecimal order_amount = new BigDecimal(total_fee).divide(BigDecimal.valueOf(100));
            String order_number = out_trade_no;
            String order_pay_type = PayTypeEnum.WECHAT.getKey();
            String order_pay_number = transaction_id;
            String order_pay_account = openid;
            String order_pay_time = time_end;
            String order_pay_result = result;

            Order order = orderService.findByOrder_number(order_number);

            boolean is_update = orderService.updateByOrder_idAndOrder_amountAndOrder_pay_typeAndOrder_pay_numberAndOrder_pay_accountAndOrder_pay_timeAndOrder_pay_result(order.getOrder_id(), order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result);

            orderProductService.updateByOrder_idAndOrder_status(order.getOrder_id(), OrderStatusEnum.PAYED.getKey());

            if (is_update) {
                renderText("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            } else {
                renderText("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[]]></return_msg></xml>");
            }

        } else {
            renderText("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[]]></return_msg></xml>");
        }
    }


    @ActionKey(Url.WECHAT_API_MENU)
    public void menu() {
        String url = null;
        try {
            url = URLEncoder.encode("http://api." + WeChat.redirect_uri + "/wechat/api/auth?url=category", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ApiResult jsonResult = MenuApi.createMenu("{\"button\":[{\"name\":\"我的健康\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的积分\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的病历\",\"key\":\"V1001_TODAY_MUSIC\"}]},{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChat.app_id + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"name\":\"服务中心\",\"sub_button\":[{\"type\":\"click\",\"name\":\"个人中心\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"在线咨询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"健康服务\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的物流\",\"key\":\"V1001_TODAY_MUSIC\"}]}]}");
//        ApiResult jsonResult = MenuApi.createMenu("{\"button\":[{\"type\":\"view\",\"name\":\"微信商城\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChat.app_id + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"}]}");

        renderText(jsonResult.getJson());
    }

    @ActionKey(Url.WECHAT_API_AUTH)
    public void auth() {
        String code = getPara("code");
        String url = getPara("url");
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }

        SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(WeChat.app_id, WeChat.app_secret, code);

        String wechat_open_id = snsAccessToken.getOpenid();

        System.out.println("http://h5." + WeChat.redirect_uri + "/#/" + url + "/?wechat_open_id=" + wechat_open_id);

        redirect("http://h5." + WeChat.redirect_uri + "/#/" + url + "/?wechat_open_id=" + wechat_open_id);
    }

    @ActionKey(Url.WECHAT_API_ORCODE)
    public void orcode() {
        ApiResult apiResult = QrcodeApi.createTemporary(604800, 1);

        System.out.println(apiResult.getJson());

        renderText(QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket")));
    }

}
