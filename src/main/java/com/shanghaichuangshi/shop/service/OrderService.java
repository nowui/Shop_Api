package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.dao.FileDao;
import com.shanghaichuangshi.dao.UserDao;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.*;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class OrderService extends Service {

    private final OrderDao orderDao = new OrderDao();

    private final SkuDao skuDao = new SkuDao();
    private final ProductDao productDao = new ProductDao();
    private final MemberDao memberDao = new MemberDao();
    private final MemberLevelDao memberLevelDao = new MemberLevelDao();
    private final CategoryService categoryService = new CategoryService();
    private final BrandDao brandDao = new BrandDao();
    private final OrderProductDao orderProductDao = new OrderProductDao();
    private final CommissionDao commissionDao = new CommissionDao();
    private final DeliveryDao deliveryDao = new DeliveryDao();
    private final FileDao fileDao = new FileDao();
    private final UserDao userDao = new UserDao();

    public int count(String order_number) {
        return orderDao.count(order_number);
    }

    public List<Order> list(String order_number, int m, int n) {
        return orderDao.list(order_number, m, n);
    }

    public List<Member> teamList(String user_id) {
        List<Member> memberList = memberDao.teamList(userDao.find(user_id).getObject_id());

        for (Member item : memberList) {
            User user = userDao.find(item.getUser_id());
            item.put(User.USER_AVATAR, user.getUser_avatar());

            List<Order> orderList = listByUser_id(item.getUser_id());
            BigDecimal member_month_order_amount = BigDecimal.ZERO;
            BigDecimal member_all_order_amount = BigDecimal.ZERO;
            for (Order order : orderList) {
                if (order.getOrder_status() && order.getOrder_is_pay()) {
                    member_month_order_amount = member_month_order_amount.add(order.getOrder_amount());
                    member_all_order_amount = member_all_order_amount.add(order.getOrder_amount());
                }
            }
            item.put(Member.MEMBER_TOTAL_AMOUNT, item.getMember_total_amount());
            item.put(Member.MEMBER_MONTH_ORDER_AMOUNT, member_month_order_amount);
            item.put(Member.MEMBER_ALL_ORDER_AMOUNT, member_all_order_amount);

            if (Util.isNullOrEmpty(item.getMember_level_id())) {
                item.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            } else {
                MemberLevel memberLevel = memberLevelDao.find(item.getMember_level_id());
                item.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            }
        }

        return memberList;
    }

    public List<Order> listByUser_id(String user_id) {
        List<Order> orderList = orderDao.listByUser_id(user_id);

        for (Order order : orderList) {
            List<OrderProduct> orderProductList = orderProductDao.listByOder_id(order.getOrder_id());
            for (OrderProduct orderProduct : orderProductList) {
                File productImageFile = fileDao.find(orderProduct.getProduct_image());
                orderProduct.put(Product.PRODUCT_IMAGE_FILE, productImageFile.getFile_thumbnail_path());

                orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, Product.PRODUCT_IMAGE_FILE, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY);
            }
            order.put(Product.PRODUCT_LIST, orderProductList);
        }

        return orderList;
    }

    public Order find(String order_id) {
        Order order = orderDao.find(order_id);

        List<OrderProduct> orderProductList = orderProductDao.listByOder_id(order_id);
        for (OrderProduct orderProduct : orderProductList) {
            File productImageFile = fileDao.find(orderProduct.getProduct_image());
            orderProduct.put(Product.PRODUCT_IMAGE_FILE, productImageFile.getFile_thumbnail_path());

            orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, Product.PRODUCT_IMAGE_FILE, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY);
        }
        order.put(Product.PRODUCT_LIST, orderProductList);

        return order;
    }

    public Order adminFind(String order_id) {
        Order order = orderDao.find(order_id);

        List<OrderProduct> orderProductList = orderProductDao.listByOder_id(order_id);
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.keep(OrderProduct.PRODUCT_ID, OrderProduct.PRODUCT_NAME, OrderProduct.ORDER_PRODUCT_PRICE, OrderProduct.ORDER_PRODUCT_QUANTITY, OrderProduct.ORDER_PRODUCT_COMMISSION);
        }
        order.put(Product.PRODUCT_LIST, orderProductList);

        return order;
    }

    public Member teamFind(String member_id) {
        Member member = memberDao.find(member_id);

        User user = userDao.find(member.getUser_id());

        member.put(User.USER_AVATAR, user.getUser_avatar());

        if (member.getMember_status()) {
            MemberLevel memberLevel = memberLevelDao.find(member.getMember_level_id());
            member.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());

            List<Order> orderList = listByUser_id(member.getUser_id());
            member.put(Order.ORDER_LIST, orderList);

            BigDecimal member_month_order_amount = BigDecimal.ZERO;
            BigDecimal member_all_order_amount = BigDecimal.ZERO;
            for (Order order : orderList) {
                if (order.getOrder_status() && order.getOrder_is_pay()) {
                    member_month_order_amount = member_month_order_amount.add(order.getOrder_amount());
                    member_all_order_amount = member_all_order_amount.add(order.getOrder_amount());
                }
            }
            member.put(Member.MEMBER_MONTH_ORDER_AMOUNT, member_month_order_amount);
            member.put(Member.MEMBER_ALL_ORDER_AMOUNT, member_all_order_amount);
        } else {
            member.put(MemberLevel.MEMBER_LEVEL_NAME, "");

            if (!member.getMember_status()) {
                Member parentMember = memberDao.find(member.getParent_id());
                MemberLevel parentMemberLevel = memberLevelDao.find(parentMember.getMember_level_id());
                List<MemberLevel> list = new ArrayList<MemberLevel>();
                List<MemberLevel> memberLevelList = memberLevelDao.listAll();
                for(MemberLevel m : memberLevelList) {
                    if (m.getMember_level_value() > parentMemberLevel.getMember_level_value()) {
                        list.add(m);
                    }
                }
                member.put(MemberLevel.MEMBER_LEVEL_LIST, list);
            }

            member.put(Order.ORDER_LIST, new JSONArray());
        }

        return member;
    }

    public List<MemberLevel> teamMemberLevelFind(String member_id) {
        Member member = memberDao.find(member_id);

        Member parentMember = memberDao.find(member.getParent_id());
        MemberLevel parentMemberLevel = memberLevelDao.find(parentMember.getMember_level_id());

        List<MemberLevel> resultList = new ArrayList<MemberLevel>();
        List<MemberLevel> memberLevelList = memberLevelDao.listAll();
        for(MemberLevel m : memberLevelList) {
            if (m.getMember_level_value() > parentMemberLevel.getMember_level_value()) {
                resultList.add(m);
            }

            if (m.getMember_level_id().equals(member.getMember_level_id())) {
                m.put(Constant.IS_SELECT, true);
            } else {
                m.put(Constant.IS_SELECT, false);
            }
        }

        return resultList;
    }

    public Map<String, String> save(Order order, JSONObject jsonObject, String request_user_id) {
        JSONArray productListJSONArray = jsonObject.getJSONArray(Product.PRODUCT_LIST);

        String open_id = jsonObject.getString("open_id");
        String pay_type = jsonObject.getString("pay_type");

        if (productListJSONArray.size() == 0) {
            throw new RuntimeException("请选购商品");
        }

        Integer order_product_quantity = 0;
        BigDecimal order_product_amount = BigDecimal.valueOf(0);
        BigDecimal order_freight_amount = BigDecimal.valueOf(0);
        BigDecimal order_discount_amount = BigDecimal.valueOf(0);

        User user = userDao.find(request_user_id);
        Member member = memberDao.find(user.getObject_id());
        JSONArray fatherMemberJSONArray = new JSONArray();

        if (member == null) {
            throw new RuntimeException("您不是我们的会员");
        }

        //会员信息
        String member_id = member.getMember_id();
        String member_level_id = member.getMember_level_id();
        String member_level_name = "";
        Integer member_level_value = 0;
        if (!Util.isNullOrEmpty(member_level_id)) {
            MemberLevel memberLevel = memberLevelDao.find(member_level_id);
            if (memberLevel != null) {
                member_level_name = memberLevel.getMember_level_name();
                member_level_value = memberLevel.getMember_level_value();
            }
        }

        JSONArray parentPathArray = new JSONArray();

        if (!Util.isNullOrEmpty(member.getParent_path())) {
            parentPathArray = JSONArray.parseArray(member.getParent_path());
        }

        //上一级信息
        for (int i = 0; i < parentPathArray.size(); i++) {
            String m_id = parentPathArray.getString(i);

            if (!m_id.equals(Constant.PARENT_ID)) {
                Member m = memberDao.find(m_id);
                MemberLevel mLevel = memberLevelDao.find(m.getMember_level_id());

                JSONObject mObject = new JSONObject();
                mObject.put(Member.MEMBER_ID, m_id);
                mObject.put(Member.MEMBER_NAME, m.getMember_name());
                mObject.put(MemberLevel.MEMBER_LEVEL_ID, mLevel.getMember_level_id());
                mObject.put(MemberLevel.MEMBER_LEVEL_NAME, mLevel.getMember_level_name());

                fatherMemberJSONArray.add(mObject);
            }
        }

        //添加自己
//        if (parentPathArray.size() > 0) {
//            JSONObject memberObject = new JSONObject();
//            memberObject.put(Member.MEMBER_ID, member_id);
//            memberObject.put(MemberLevel.MEMBER_LEVEL_ID, member_level_id);
//            memberObject.put(MemberLevel.MEMBER_LEVEL_NAME, member_level_name);
//            memberObject.put(MemberLevel.MEMBER_LEVEL_VALUE, member_level_value);
//            member_path.add(memberObject);
//        }

        //商品信息
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
        for (int i = 0; i < productListJSONArray.size(); i++) {
            JSONObject productJSONObject = productListJSONArray.getJSONObject(i);

            String order_product_id = Util.getRandomUUID();
            String sku_id = productJSONObject.getString(Sku.SKU_ID);
            Integer product_quantity = productJSONObject.getInteger(Product.PRODUCT_QUANTITY);

            Sku sku = skuDao.find(sku_id);

            if (sku == null) {
                throw new RuntimeException("SKU不存在:" + sku_id);
            }

            Product product = productDao.find(sku.getProduct_id());

//            if (product_quantity > sku.getProduct_stock()) {
//                throw new RuntimeException("库存不足:" + product.getProduct_name());
//            }

            //更新订单商品数量
            order_product_quantity += product_quantity;

            //更新商品应付价格
            JSONObject productPriceJSONObject = skuDao.findProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);
            order_product_amount = order_product_amount.add(product_price.multiply(BigDecimal.valueOf(product_quantity)));

            //订单的商品
            Category category = categoryService.find(product.getCategory_id());
            Brand brand = brandDao.find(product.getBrand_id());

            //佣金
            String commission_id = "";
            Commission commission = null;
            List<Commission> commissionList = commissionDao.list(product.getProduct_id());
            for (Commission c : commissionList) {
                if (c.getProduct_attribute().equals(sku.getProduct_attribute())) {
                    commission_id = c.getCommission_id();

                    commission = c;
                }
            }
            if (commission_id != "") {
                JSONArray productCommissionJsonArray = JSONArray.parseArray(commission.getProduct_commission());

                for (int j = 0; j < fatherMemberJSONArray.size(); j++) {
                    JSONObject fatherMemberJSONObject = fatherMemberJSONArray.getJSONObject(j);
                    String father_member_level_id = fatherMemberJSONObject.getString(MemberLevel.MEMBER_LEVEL_ID);

                    for (int k = 0; k < productCommissionJsonArray.size(); k++) {
                        JSONObject productCommissionJsonObject = productCommissionJsonArray.getJSONObject(k);

                        if (father_member_level_id.equals(productCommissionJsonObject.getString(MemberLevel.MEMBER_LEVEL_ID))) {
                            BigDecimal product_commission = productCommissionJsonObject.getBigDecimal(Commission.PRODUCT_COMMISSION);
                            BigDecimal commission_amount = order_product_amount.multiply(product_commission).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);

                            fatherMemberJSONObject.put(Commission.COMMISSION_AMOUNT, commission_amount);
                            fatherMemberJSONObject.put(Commission.PRODUCT_COMMISSION, product_commission);

                            break;
                        }
                    }
                }
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder_product_id(order_product_id);
            orderProduct.setOrder_status(false);
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
            orderProduct.setUser_id(request_user_id);
            orderProduct.setMember_id(member_id);
            orderProduct.setCommission_id(commission_id);
            orderProduct.setOrder_product_commission(fatherMemberJSONArray.toJSONString());
            orderProduct.setProduct_attribute(sku.getProduct_attribute());
            orderProduct.setProduct_market_price(product.getProduct_market_price());
            orderProduct.setProduct_price(product.getProduct_price());
            orderProduct.setProduct_stock(product.getProduct_stock());
            orderProduct.setOrder_product_price(product_price);
            orderProduct.setOrder_product_quantity(product_quantity);
            orderProductList.add(orderProduct);
        }

        order.setMember_id(member_id);
        order.setMember_level_id(member_level_id);
        order.setMember_level_name(member_level_name);
        order.setMember_level_value(member_level_value);
        order.setOrder_product_quantity(order_product_quantity);
        order.setOrder_product_amount(order_product_amount);
        order.setOrder_freight_amount(order_freight_amount);
        order.setOrder_discount_amount(order_discount_amount);
        order.setOrder_amount(order_product_amount.subtract(order_freight_amount).subtract(order_discount_amount));
        order.setOrder_flow(OrderFlowEnum.WAIT_PAY.getKey());
        order.setOrder_status(false);

        Order o = orderDao.save(order, request_user_id);

        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.setOrder_id(o.getOrder_id());
        }
        orderProductDao.save(orderProductList, request_user_id);

        return unifiedorder(o, open_id, pay_type);
    }

    public Map<String, String> pay(String order_id, JSONObject jsonObject, String request_user_id) {
        String open_id = jsonObject.getString("open_id");
        String pay_type = jsonObject.getString("pay_type");

        Order order = orderDao.find(order_id);

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
        return orderDao.findByOrder_number(order_number);
    }

    public boolean update(String order_id, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result, String order_flow, Boolean order_status) {
        return orderDao.update(order_id, order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result, order_flow, order_status);
    }

    public boolean delete(Order order, String request_user_id) {
        return orderDao.delete(order.getOrder_id(), request_user_id);
    }

    public Map<String, Object> check(String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Delivery delivery = deliveryDao.findDefaultByUser_id(request_user_id);
        if (delivery == null) {
            List<Delivery> deliveryList = deliveryDao.listByUser_id(request_user_id, 0, 0);

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
        Order order = orderDao.find(order_id);

        if (!order.getOrder_is_pay()) {
            orderDao.updateByOrder_idAndOrder_is_confirm(order_id);
        }

        return order;
    }

}