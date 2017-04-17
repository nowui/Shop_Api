package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
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
import java.util.*;

public class OrderService extends Service {

    private final OrderDao orderDao = new OrderDao();

    private final SkuService skuService = new SkuService();
    private final ProductService productService = new ProductService();
    private final MemberService memberService = new MemberService();
    private final MemberLevelService memberLevelService = new MemberLevelService();
    private final CategoryService categoryService = new CategoryService();
    private final BrandService brandService = new BrandService();
    private final OrderProductService orderProductService = new OrderProductService();
    private final CommissionService commissionService = new CommissionService();

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
        Order order = orderDao.find(order_id);

        List<OrderProduct> orderProductList = orderProductService.list(order_id);
        order.put("product", orderProductList);

        return order;
    }

    public Order adminFind(String order_id) {
        return orderDao.find(order_id);
    }

    public Map<String, String> save(Order order, JSONObject jsonObject, String request_user_id) {
        JSONArray productJSONArray = jsonObject.getJSONArray(Product.PRODUCT_LIST);

        String open_id = jsonObject.getString("open_id");

        if (productJSONArray.size() == 0) {
            throw new RuntimeException("请选购商品");
        }

        Integer order_product_quantity = 0;
        BigDecimal order_product_amount = BigDecimal.valueOf(0);
        BigDecimal order_freight_amount = BigDecimal.valueOf(0);
        BigDecimal order_discount_amount = BigDecimal.valueOf(0);
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
            Integer product_quantity = productJSONObject.getInteger(OrderProduct.PRODUCT_QUANTITY);

            Sku sku = skuService.find(sku_id);

            if (sku == null) {
                throw new RuntimeException("SKU不存在:" + sku_id);
            }

            Product product = productService.find(sku.getProduct_id());

            if (product_quantity > sku.getProduct_stock()) {
                throw new RuntimeException("库存不足:" + product.getProduct_name());
            }

            String commission_id = "";

            List<Commission> commissionList = commissionService.list(product.getProduct_id());

            for (Commission commission : commissionList) {
                if (commission.getProduct_attribute().equals(sku.getProduct_attribute())) {
                    commission_id = commission.getCommission_id();
                }
            }

            //更新订单商品数量
            order_product_quantity += product_quantity;

            //更新订单应付价格
            JSONObject productPriceJSONObject = skuService.getProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);
            order_product_amount = order_product_amount.add(product_price.multiply(BigDecimal.valueOf(product_quantity)));

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
            orderProduct.setSku_id(sku_id);
            orderProduct.setCommission_id(commission_id);
            orderProduct.setProduct_attribute(sku.getProduct_attribute());
            orderProduct.setProduct_market_price(product.getProduct_market_price());
            orderProduct.setProduct_price(product.getProduct_price());
            orderProduct.setProduct_stock(product.getProduct_stock());
            orderProduct.setProduct_quantity(product_quantity);
            orderProductList.add(orderProduct);
        }

        order.setOrder_product_quantity(order_product_quantity);
        order.setOrder_product_amount(order_product_amount);
        order.setOrder_freight_amount(order_freight_amount);
        order.setOrder_discount_amount(order_discount_amount);
        order.setOrder_amount(order_product_amount.subtract(order_freight_amount).subtract(order_discount_amount));
        order.setMember_level_id(member_level_id);
        order.setMember_level_name(member_level_name);
        order.setMember_level_value(member_level_value);
        order.setOrder_status(OrderStatusEnum.WAIT.getKey());

        Order o = orderDao.save(order, request_user_id);

        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.setOrder_id(o.getOrder_id());
        }
        orderProductService.save(orderProductList, request_user_id);

        return unifiedorder(o, open_id);
    }

    public Map<String, String> pay(String order_id, JSONObject jsonObject, String request_user_id) {
        String open_id = jsonObject.getString("open_id");

        Order order = orderDao.find(order_id);

        if (order.getOrder_is_pay() || !order.getUser_id().equals(request_user_id)) {
            return new HashMap<String, String>();
        }

        return unifiedorder(order, open_id);
    }

    public Map<String, String> unifiedorder(Order order, String open_id) {
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
        parameter.put("appid", WeChat.app_id);
        parameter.put("body", body);
        parameter.put("mch_id", WeChat.mch_id);
        parameter.put("nonce_str", nonce_str);
        parameter.put("notify_url", notify_url);
        parameter.put("openid", openid);
        parameter.put("out_trade_no", out_trade_no);
        parameter.put("spbill_create_ip", spbill_create_ip);
        parameter.put("total_fee", total_fee);
        parameter.put("trade_type", trade_type);
        parameter.put("sign", PaymentKit.createSign(parameter, WeChat.mch_key));

        System.out.println("--------------------------");
        System.out.println(JSONObject.toJSONString(parameter));
        System.out.println("--------------------------");

        String result = HttpKit.post("https://api.mch.weixin.qq.com/pay/unifiedorder", PaymentKit.toXml(parameter));

        Map<String, String> map = PaymentKit.xmlToMap(result);

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String prepay_id = map.get("prepay_id");
        String package_str = "prepay_id=" + prepay_id;
        String signType = "MD5";

        SortedMap<String, String> parameter2 = new TreeMap<String, String>();
        parameter2.put("appId", WeChat.app_id);
        parameter2.put("timeStamp", timestamp);
        parameter2.put("nonceStr", nonce_str);
        parameter2.put("package", package_str);
        parameter2.put("signType", signType);
        parameter2.put("paySign", PaymentKit.createSign(parameter2, WeChat.mch_key));
        parameter2.put("orderId", order.getOrder_id());

        return parameter2;
    }

    public boolean update(Order order, String request_user_id) {
        return orderDao.update(order, request_user_id);
    }

    public boolean updateByOrder_numberAndOrder_amountAndOrder_pay_typeAndOrder_pay_numberAndOrder_pay_accountAndOrder_pay_timeAndOrder_pay_result(String order_number, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result) {
        return orderDao.updateByOrder_numberAndOrder_amountAndOrder_pay_typeAndOrder_pay_numberAndOrder_pay_accountAndOrder_pay_timeAndOrder_pay_result(order_number, order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result);
    }

    public boolean delete(Order order, String request_user_id) {
        return orderDao.delete(order.getOrder_id(), request_user_id);
    }

    public Order confirm(String order_id, String request_user_id) {
        Order order = orderDao.find(order_id);

        if (!order.getOrder_is_pay()) {
            orderDao.updateByOrder_idAndOrder_is_confirm(order_id);
        }

        return order;
    }

}