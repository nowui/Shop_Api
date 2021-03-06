package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.shop.service.*;
import com.shanghaichuangshi.shop.type.BillFlowEnum;
import com.shanghaichuangshi.shop.type.BillTypeEnum;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.shop.type.PayTypeEnum;
import com.shanghaichuangshi.util.HttpUtil;
import com.shanghaichuangshi.util.Util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WeChatApiController extends ApiController {

    private final OrderService orderService = new OrderService();
    private final OrderProductService orderProductService = new OrderProductService();
    private final MemberService memberService = new MemberService();
    private final BillService billService = new BillService();

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

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
        for (byte b : hash) {
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

        String wx_app_id = WeChat.wx_app_id;
        String mch_key = WeChat.mch_key;
        if (appid.equals(wx_app_id)) {
            mch_key = WeChat.wx_mch_key;
        }

        String endsign = PaymentKit.createSign(parameter, mch_key);

        if (sign.equals(endsign)) {
            BigDecimal order_amount = new BigDecimal(total_fee).divide(BigDecimal.valueOf(100));
            String order_number = out_trade_no;
            String order_pay_type = PayTypeEnum.WECHAT.getKey();
            String order_pay_number = transaction_id;
            String order_pay_account = openid;
            String order_pay_time = time_end;
            String order_pay_result = result;
            Boolean order_status = true;

            Order order = orderService.findByOrder_number(order_number);

            boolean is_update = orderService.updateSend(order.getOrder_id(), order.getUser_id(), order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result, order_status);

            orderProductService.updateByOrder_idAndOrder_statusAndUser_id(order.getOrder_id(), order_status, order.getUser_id());

            Member member = memberService.find(order.getMember_id());

            List<Bill> billList = new ArrayList<Bill>();
            String request_user_id = "";


            List<OrderProduct> orderProductList = orderProductService.listByOder_id(order.getOrder_id());
            for (OrderProduct orderProduct : orderProductList) {
                String commission_id = orderProduct.getCommission_id();
//                BigDecimal order_product_price = orderProduct.getOrder_product_price();
//                Integer order_product_quantity = orderProduct.getOrder_product_quantity();

//                BigDecimal price = order_product_price.multiply(BigDecimal.valueOf(order_product_quantity));

                //如果要分成
                if (!Util.isNullOrEmpty(commission_id)) {
                    JSONArray orderProductCommissionJSONArray = JSONArray.parseArray(orderProduct.getOrder_product_commission());

                    for (int i = 0; i < orderProductCommissionJSONArray.size(); i++) {

                        JSONObject orderProductCommissionJSONObject = orderProductCommissionJSONArray.getJSONObject(i);

                        String parent_member_id = orderProductCommissionJSONObject.getString(Member.MEMBER_ID);
                        BigDecimal commission_amount = orderProductCommissionJSONObject.getBigDecimal(Commission.COMMISSION_AMOUNT);

                        Member parentMember = memberService.find(parent_member_id);

                        //账单
                        Bill bill = new Bill();
                        bill.setUser_id(parentMember.getUser_id());
                        bill.setObject_id(orderProduct.getOrder_product_id());
                        bill.setBill_type(BillTypeEnum.COMMISSION.getKey());
                        bill.setBill_image(orderProduct.getProduct_image());
                        bill.setBill_amount(commission_amount);
                        bill.setBill_name(orderProduct.getProduct_name());
                        bill.setBill_is_income(true);
                        bill.setBill_time(new Date());
                        bill.setBill_flow(BillFlowEnum.WAIT.getKey());
                        bill.setBill_status(true);
                        billList.add(bill);
                    }
                }
            }

            //本人下单账单
            Bill bill = new Bill();
            bill.setUser_id(member.getUser_id());
            bill.setObject_id(order.getOrder_id());
            bill.setBill_type(BillTypeEnum.ORDER.getKey());
            bill.setBill_image("");
            bill.setBill_amount(order.getOrder_amount());
            bill.setBill_name(order_number);
            bill.setBill_is_income(false);
            bill.setBill_time(new Date());
            bill.setBill_flow(BillFlowEnum.FINISH.getKey());
            bill.setBill_status(true);
            billList.add(bill);

            billService.save(billList, request_user_id);

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
            url = URLEncoder.encode("http://api." + WeChat.redirect_uri + "/wechat/api/auth?url=home", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ApiResult jsonResult;

        if (WeChat.mch_id == "1407140202") {
//            jsonResult = MenuApi.createMenu("{\"button\":[{\"type\":\"view\",\"name\":\"微信商城\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/home/\"}]\"button\":[{\"type\":\"click\",\"name\":\"小程序\",\"key\":\"V1001_TODAY_MUSIC\"}]}");
            jsonResult = MenuApi.createMenu("{\"button\":[{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/home/\"}]\"button\":[{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"}]}");
        } else {
//            jsonResult = MenuApi.createMenu("{\"button\":[{\"name\":\"我的健康\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的积分\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的病历\",\"key\":\"V1001_TODAY_MUSIC\"}]},{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/home/\"},{\"name\":\"服务中心\",\"sub_button\":[{\"type\":\"click\",\"name\":\"个人中心\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"在线咨询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"健康服务\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的物流\",\"key\":\"V1001_TODAY_MUSIC\"}]}]}");
//            jsonResult = MenuApi.createMenu("{\"button\":[{\"name\":\"我的健康\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的积分\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"查询医生\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的病历\",\"key\":\"V1001_TODAY_MUSIC\"}]},{\"type\":\"view\",\"name\":\"快速购买\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/home/\"},{\"name\":\"服务中心\",\"sub_button\":[{\"type\":\"click\",\"name\":\"个人中心\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"在线咨询\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"健康服务\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"我的物流\",\"key\":\"V1001_TODAY_MUSIC\"}]}]}");
            jsonResult = MenuApi.createMenu("{\"button\":[{\"type\":\"view\",\"name\":\"健康推荐\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/home/\"},{\"type\":\"view\",\"name\":\"睡前故事\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/story/index/\"},{\"type\":\"view\",\"name\":\"医学科普\",\"url\":\"http://h5." + WeChat.redirect_uri + "/#/science/index/\"}]}");
        }

        renderText(jsonResult.getJson());
    }

    @ActionKey(Url.WECHAT_API_LOGIN)
    public void login() {
        String code = getPara("code");

        SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(WeChat.app_id, WeChat.app_secret, code);

        String open_id = snsAccessToken.getOpenid();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("open_id", open_id);

        renderJson(resultMap);
    }

    @ActionKey(Url.WECHAT_API_AUTH)
    public void auth() {
        String code = getPara("code");
        String url = getPara("url");
        String platform = getPara("platform");
        String version = getPara("version");
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }

        if (Util.isNullOrEmpty(code)) {
            redirect("http://h5." + WeChat.redirect_uri + "/#/home/");
        } else {
            SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(WeChat.app_id, WeChat.app_secret, code);

            String wechat_union_id = "";
            if (Util.isNullOrEmpty(snsAccessToken.getUnionid())) {
                wechat_union_id = snsAccessToken.getUnionid();
            }

            String wechat_open_id = snsAccessToken.getOpenid();
            String ip_address = HttpUtil.getIpAddress(getRequest());
            String request_user_id = "";

            ApiResult apiResult = UserApi.getUserInfo(wechat_open_id);


            String user_name = apiResult.getStr("nickname");
            String user_avatar = apiResult.getStr("headimgurl");
            String scene_id = "";
            Boolean member_status = false;

            if (Util.isNullOrEmpty(user_name)) {
                user_name = "";
            }

            if (Util.isNullOrEmpty(user_avatar)) {
                user_avatar = "";
            }

            Map<String, Object> resultMap = memberService.weChatH5Login(wechat_open_id, wechat_union_id, user_name, user_avatar, scene_id, member_status, platform, version, ip_address, request_user_id);
            String token = resultMap.get(Constant.TOKEN.toLowerCase()).toString();

            redirect("http://h5." + WeChat.redirect_uri + "/#/" + url + "/?open_id=" + wechat_open_id + "&token=" + token);
        }
    }

    @ActionKey(Url.WECHAT_API_ORCODE)
    public void orcode() {
        ApiResult apiResult = QrcodeApi.createTemporary(604800, 1);

        renderText(QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket")));
    }

}
