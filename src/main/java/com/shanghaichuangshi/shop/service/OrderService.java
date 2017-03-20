package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.OrderDao;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.OrderStatusEnum;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService extends Service {

    private static final OrderDao orderDao = new OrderDao();

    private static final SkuService skuService = new SkuService();
    private static final ProductService productService = new ProductService();
    private static final MemberService memberService = new MemberService();
    private static final MemberLevelService memberLevelService = new MemberLevelService();
    private static final CategoryService categoryService = new CategoryService();
    private static final BrandService brandService = new BrandService();
    private static final OrderProductService orderProductService = new OrderProductService();

    public int count(Order order) {
        return orderDao.count(order.getOrder_number());
    }

    public List<Order> list(Order order, int m, int n) {
        return orderDao.list("", m, n);
    }

    public List<Order> listByUser_id(String user_id, Integer m, Integer n) {
        return orderDao.listByUser_id(user_id, m, n);
    }

    public Order find(String order_id) {
        return orderDao.find(order_id);
    }

    public Map<String, String> save(Order order, JSONObject jsonObject, String request_user_id) {
        JSONArray productJSONArray = jsonObject.getJSONArray(Product.PRODUCT_LIST);

        if (productJSONArray.size() == 0) {
            throw new RuntimeException("请选购商品");
        }

        Integer order_product_number = 0;
        BigDecimal order_receivable_amount = BigDecimal.valueOf(0);
        String member_level_id = memberService.findMember_lever_idByUser_id(request_user_id);
        String member_level_name = "";
        Integer member_level_value = 0;
        if (!Util.isNullOrEmpty(member_level_id)) {
            MemberLevel memberLevel = memberLevelService.find(member_level_id);
            if (memberLevel != null) {
                member_level_name = memberLevel.getMember_level_name();
                member_level_value = memberLevel.getMember_level_value();
            }
        }
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

        for (int i = 0; i < productJSONArray.size(); i++) {
            JSONObject productJSONObject = productJSONArray.getJSONObject(i);

            String sku_id = productJSONObject.getString(Sku.SKU_ID);
            Integer product_number = productJSONObject.getInteger(OrderProduct.PRODUCT_NUMBER);

            Sku sku = skuService.find(sku_id);

            if (sku == null) {
                throw new RuntimeException("SKU不存在:" + sku_id);
            }

            Product product = productService.find(sku.getProduct_id());

            if (product_number > sku.getProduct_stock()) {
                throw new RuntimeException("库存不足:" + product.getProduct_name());
            }

            //更新订单商品数量
            order_product_number += product_number;

            //更新订单应付价格
            JSONObject productPriceJSONObject = skuService.getProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);
            order_receivable_amount = order_receivable_amount.add(product_price.multiply(BigDecimal.valueOf(product_number)));

            //订单的商品
            Category category = categoryService.find(product.getCategory_id());
            Brand brand = brandService.find(product.getBrand_id());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct_id(product.getProduct_id());
            orderProduct.setCategory_id(category.getCategory_id());
            orderProduct.setCategory_name(category.getCategory_name());
            orderProduct.setBrand_id(brand.getBrand_id());
            orderProduct.setBrand_name(brand.getBrand_name());
            orderProduct.setProduct_name(product.getProduct_name());
            orderProduct.setProduct_image(product.getProduct_image());
            orderProduct.setProduct_image_list(product.getProduct_image_list());
            orderProduct.setProduct_is_new(product.getProduct_is_new());
            orderProduct.setProduct_is_recommend(product.getProduct_is_recommend());
            orderProduct.setProduct_is_bargain(product.getProduct_is_bargain());
            orderProduct.setProduct_is_hot(product.getProduct_is_hot());
            orderProduct.setProduct_is_sale(product.getProduct_is_sale());
            orderProduct.setProduct_content(product.getProduct_content());
            orderProduct.setSku_id(sku.getSku_id());
            orderProduct.setProduct_attribute(sku.getProduct_attribute());
            orderProduct.setProduct_market_price(product.getProduct_market_price());
            orderProduct.setProduct_price(product.getProduct_price());
            orderProduct.setProduct_stock(product.getProduct_stock());
            orderProduct.setProduct_number(product_number);
            orderProductList.add(orderProduct);
        }

        order.setOrder_product_number(order_product_number);
        order.setOrder_receivable_amount(order_receivable_amount);
        order.setMember_level_id(member_level_id);
        order.setMember_level_name(member_level_name);
        order.setMember_level_value(member_level_value);
        order.setOrder_status(OrderStatusEnum.WAIT.getKey());

        Order o = orderDao.save(order, request_user_id);

        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.setOrder_id(o.getOrder_id());
        }
        orderProductService.save(orderProductList, request_user_id);

        return unifiedorder(o);
    }

    public Map<String, String> unifiedorder(Order order) {
        String nonce_str = Util.getRandomStringByLength(32);
        String body = "上海星销信息技术有限公司";
        String notify_url = "http://api.jiyiguan.nowui.com/wechat/api/notify";
        String openid = "oqvzXv4c-FY2-cGh9U-RA4JIrZoc";
        String out_trade_no = order.getOrder_number();
        String spbill_create_ip = "0.0.0.0";
        DecimalFormat format = new DecimalFormat("0");
        String total_fee = format.format(order.getOrder_receivable_amount().multiply(BigDecimal.valueOf(100)));
        String trade_type = "JSAPI";

        String stringA = "appid=" + WeChat.app_id + "&body=" + body + "&mch_id=" + WeChat.mch_id + "&nonce_str=" + nonce_str + "&notify_url=" + notify_url + "&openid=" + openid + "&out_trade_no=" + out_trade_no + "&spbill_create_ip=" + spbill_create_ip + "&total_fee=" + total_fee + "&trade_type=" + trade_type;
        String stringSignTemp = stringA + "&key=" + WeChat.mch_key;
        String sign = HashKit.md5(stringSignTemp).toUpperCase();

        StringBuffer bf = new StringBuffer();

        bf.append("<xml>");

        bf.append("<appid><![CDATA[");
        bf.append(WeChat.app_id);
        bf.append("]]></appid>");

        bf.append("<body><![CDATA[");
        bf.append(body);
        bf.append("]]></body>");

        bf.append("<mch_id><![CDATA[");
        bf.append(WeChat.mch_id);
        bf.append("]]></mch_id>");

        bf.append("<nonce_str><![CDATA[");
        bf.append(nonce_str);
        bf.append("]]></nonce_str>");

        bf.append("<notify_url><![CDATA[");
        bf.append(notify_url);
        bf.append("]]></notify_url>");

        bf.append("<openid><![CDATA[");
        bf.append(openid);
        bf.append("]]></openid>");

        bf.append("<out_trade_no><![CDATA[");
        bf.append(out_trade_no);
        bf.append("]]></out_trade_no>");

        bf.append("<spbill_create_ip><![CDATA[");
        bf.append(spbill_create_ip);
        bf.append("]]></spbill_create_ip>");

        bf.append("<total_fee><![CDATA[");
        bf.append(total_fee);
        bf.append("]]></total_fee>");

        bf.append("<trade_type><![CDATA[");
        bf.append(trade_type);
        bf.append("]]></trade_type>");

        bf.append("<sign><![CDATA[");
        bf.append(sign);
        bf.append("]]></sign>");

        bf.append("</xml>");

        String result = HttpKit.post("https://api.mch.weixin.qq.com/pay/unifiedorder", bf.toString());

        Map<String, String> map = PaymentKit.xmlToMap(result);

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String prepay_id = map.get("prepay_id");
        String package_str = "prepay_id=" + prepay_id;
        String signType = "MD5";

        stringA = "appId=" + WeChat.app_id + "&nonceStr=" + nonce_str + "&package=" + package_str + "&signType=" + signType + "&timeStamp=" + timestamp;
        stringSignTemp = stringA + "&key=" + WeChat.mch_key;
        sign = HashKit.md5(stringSignTemp).toUpperCase();

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("appId", WeChat.app_id);
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("package", package_str);
        resultMap.put("signType", signType);
        resultMap.put("paySign", sign);

        System.out.println(JSONObject.toJSONString(resultMap));

        return resultMap;
    }

    public boolean update(Order order, String request_user_id) {
        return orderDao.update(order, request_user_id);
    }

    public boolean delete(Order order, String request_user_id) {
        return orderDao.delete(order.getOrder_id(), request_user_id);
    }

}