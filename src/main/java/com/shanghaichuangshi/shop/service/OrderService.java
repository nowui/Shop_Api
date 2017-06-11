package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.cache.FileCache;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.shop.cache.*;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class OrderService extends Service {

    private final OrderCache orderCache = new OrderCache();
    private final OrderProductCache orderProductCache = new OrderProductCache();
    private final DeliveryCache deliveryCache = new DeliveryCache();
    private final FileCache fileCache = new FileCache();
    private final ExpressCache expressCache = new ExpressCache();

    public int count(String order_number, String order_flow) {
        return orderCache.count(order_number, order_flow);
    }

    public int countByUser_idAndOrder_flow(String user_id, String order_flow) {
        return orderCache.countByUser_idAndOrder_flow(user_id, order_flow);
    }

    public List<Order> list(String order_number, String order_flow, int m, int n) {
        return orderCache.list(order_number, order_flow, m, n);
    }

    public List<Order> listByUser_id(String user_id) {
        List<Order> orderList = orderCache.listByUser_id(user_id);

        for (Order order : orderList) {
            List<OrderProduct> orderProductList = orderProductCache.listByOder_id(order.getOrder_id());
            for (OrderProduct orderProduct : orderProductList) {
                File productImageFile = fileCache.find(orderProduct.getProduct_image());
                orderProduct.put(Product.PRODUCT_IMAGE_FILE, productImageFile.getFile_thumbnail_path());

                orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, Product.PRODUCT_IMAGE_FILE, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY);
            }
            order.put(Product.PRODUCT_LIST, orderProductList);
        }

        return orderList;
    }

    public List<Express> adminExpressList(String order_id) {
        return expressCache.listByOrder_id(order_id);
    }

    public Order find(String order_id) {
        Order order = orderCache.find(order_id);

        List<OrderProduct> orderProductList = orderProductCache.listByOder_id(order_id);
        for (OrderProduct orderProduct : orderProductList) {
            File productImageFile = fileCache.find(orderProduct.getProduct_image());
            orderProduct.put(Product.PRODUCT_IMAGE_FILE, productImageFile.getFile_thumbnail_path());

            orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, Product.PRODUCT_IMAGE_FILE, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY);
        }
        order.put(Product.PRODUCT_LIST, orderProductList);

        return order;
    }

    public Order adminFind(String order_id) {
        Order order = orderCache.find(order_id);

        List<OrderProduct> orderProductList = orderProductCache.listByOder_id(order_id);
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY, OrderProduct.ORDER_PRODUCT_COMMISSION);
        }
        order.put(Product.PRODUCT_LIST, orderProductList);

        List<Express> expressList = expressCache.listByOrder_id(order_id);
        order.put(Product.EXPRESS_LIST, expressList);

        return order;
    }

    public Map<String, String> save(String order_id, String member_id, String member_level_id, String member_level_name, Integer member_level_value, Integer order_product_quantity, BigDecimal order_product_amount, BigDecimal order_freight_amount, BigDecimal order_discount_amount, String open_id, String pay_type, String request_user_id) {
        deleteCountByUser_idAndOrder_flow(request_user_id, OrderFlowEnum.WAIT_SEND.getKey());

        Order order = orderCache.save(order_id, member_id, member_level_id, member_level_name, member_level_value, order_product_quantity, order_product_amount, order_freight_amount, order_discount_amount, request_user_id);

        deleteCountByUser_idAndOrder_flow(request_user_id, OrderFlowEnum.WAIT_PAY.getKey());

        return unifiedorder(order, open_id, pay_type);
    }

    public Map<String, String> pay(String order_id, JSONObject jsonObject, String request_user_id) {
        String open_id = jsonObject.getString("open_id");
        String pay_type = jsonObject.getString("pay_type");

        Order order = orderCache.find(order_id);

        if (order.getOrder_is_pay() || !order.getUser_id().equals(request_user_id)) {
            return new HashMap<String, String>();
        }

        return unifiedorder(order, open_id, pay_type);
    }

    public Map<String, String> unifiedorder(Order order, String open_id, String pay_type) {
        String app_id = WeChat.app_id;
        String mch_id = WeChat.mch_id;
        String mch_key = WeChat.mch_key;
        if (pay_type.equals("WX")) {
            app_id = WeChat.wx_app_id;
            mch_id = WeChat.wx_mch_id;
            mch_key = WeChat.wx_mch_key;
        }

        String nonce_str = Util.getRandomStringByLength(32);
        String body = WeChat.body;
        String notify_url = WeChat.notify_url;
        String openid = open_id;
        String out_trade_no = order.getOrder_number();
        String spbill_create_ip = "0.0.0.0";
        DecimalFormat format = new DecimalFormat("0");
        String total_fee = format.format(order.getOrder_amount().multiply(BigDecimal.valueOf(100)));
        String trade_type = "JSAPI";

        SortedMap<String, String> parameter = new TreeMap<String, String>();
        parameter.put("appid", app_id);
        parameter.put("body", body);
        parameter.put("mch_id", mch_id);
        parameter.put("nonce_str", nonce_str);
        parameter.put("notify_url", notify_url);
        parameter.put("openid", openid);
        parameter.put("out_trade_no", out_trade_no);
        parameter.put("spbill_create_ip", spbill_create_ip);
        parameter.put("total_fee", total_fee);
        parameter.put("trade_type", trade_type);
        parameter.put("sign", PaymentKit.createSign(parameter, mch_key));

        String result = HttpKit.post("https://api.mch.weixin.qq.com/pay/unifiedorder", PaymentKit.toXml(parameter));

        Map<String, String> map = PaymentKit.xmlToMap(result);

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String prepay_id = map.get("prepay_id");
        String package_str = "prepay_id=" + prepay_id;
        String signType = "MD5";

        SortedMap<String, String> parameter2 = new TreeMap<String, String>();
        parameter2.put("appId", app_id);
        parameter2.put("timeStamp", timestamp);
        parameter2.put("nonceStr", nonce_str);
        parameter2.put("package", package_str);
        parameter2.put("signType", signType);
        parameter2.put("paySign", PaymentKit.createSign(parameter2, mch_key));
        parameter2.put("orderId", order.getOrder_id());

        return parameter2;
    }

    public Order findByOrder_number(String order_number) {
        return orderCache.findByOrder_number(order_number);
    }

    public boolean update(String order_id, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result, String order_flow, Boolean order_status) {
        return orderCache.update(order_id, order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result, order_flow, order_status);
    }

    public void updateReceive(String order_id, String request_user_id) {
        Order order = orderCache.find(order_id);
        if (order.getOrder_flow().equals(OrderFlowEnum.WAIT_SEND.getKey())) {
            orderCache.updateReceive(order_id, request_user_id);
        }
    }

    public void updateFinish(List<String> orderIdList) {
        orderCache.updateFinish(orderIdList);
    }

    public boolean delete(Order order, String request_user_id) {
        List<Order> orderList = listByUser_id(request_user_id);

        Boolean isExit = false;
        for (Order item : orderList) {
            if (item.getOrder_id().equals(order.getOrder_id())) {
                isExit = true;
            }
        }

        if (isExit) {
            return orderCache.delete(order.getOrder_id(), request_user_id);
        } else {
            throw new RuntimeException("您没有该订单");
        }
    }

    public Map<String, Object> check(String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Delivery delivery = deliveryCache.findDefaultByUser_id(request_user_id);
        if (delivery == null) {
            List<Delivery> deliveryList = deliveryCache.listByUser_id(request_user_id, 0, 0);

            if (deliveryList.size() == 0) {
                resultMap.put(Delivery.DELIVERY_NAME, "");
                resultMap.put(Delivery.DELIVERY_PHONE, "");
                resultMap.put(Delivery.DELIVERY_ADDRESS, "");
            } else {
                resultMap.put(Delivery.DELIVERY_NAME, deliveryList.get(0).getDelivery_name());
                resultMap.put(Delivery.DELIVERY_PHONE, deliveryList.get(0).getDelivery_phone());
                resultMap.put(Delivery.DELIVERY_ADDRESS, deliveryList.get(0).getDelivery_address());
            }
        } else {
            resultMap.put(Delivery.DELIVERY_NAME, delivery.getDelivery_name());
            resultMap.put(Delivery.DELIVERY_PHONE, delivery.getDelivery_phone());
            resultMap.put(Delivery.DELIVERY_ADDRESS, delivery.getDelivery_address());
        }
        resultMap.put("freight", "0");

        return resultMap;
    }

    public Order confirm(String order_id, String request_user_id) {
        Order order = orderCache.find(order_id);

        if (!order.getOrder_is_pay()) {
            orderCache.updateByOrder_idAndOrder_is_confirm(order_id);
        }

        return order;
    }

    public void deleteCountByUser_idAndOrder_flow(String user_id, String order_flow) {
        orderCache.deleteCountByUser_idAndOrder_flow(user_id, order_flow);
    }

}