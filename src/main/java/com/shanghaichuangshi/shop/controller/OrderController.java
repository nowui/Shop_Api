package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.shop.service.*;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderController extends Controller {

    private final OrderService orderService = new OrderService();
    private final MemberService memberService = new MemberService();
    private final SkuService skuService = new SkuService();
    private final MemberLevelService memberLevelService = new MemberLevelService();
    private final OrderProductService orderProductService = new OrderProductService();

    @ActionKey(Url.ORDER_LIST)
    public void list() {
//        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        String request_user_id = getRequest_user_id();

        List<Order> orderListvice = orderService.listByUser_id(request_user_id);

        renderSuccessJson(orderListvice);
    }

    @ActionKey(Url.ORDER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_NUMBER);

        int count = orderService.count(model.getOrder_number(), model.getOrder_flow());

        List<Order> orderListvice = orderService.list(model.getOrder_number(), model.getOrder_flow(), getM(), getN());

        renderSuccessJson(count, orderListvice);
    }

    @ActionKey(Url.ORDER_ADMIN_EXPRESS_LIST)
    public void adminExpressList() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        List<Express> expressList = orderService.adminExpressList(model.getOrder_id());

        renderSuccessJson(expressList);
    }

    @ActionKey(Url.ORDER_FIND)
    public void find() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.find(model.getOrder_id());

        renderSuccessJson(order.removeUnfindable());
    }

    @ActionKey(Url.ORDER_ADMIN_FIND)
    public void adminFind() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.adminFind(model.getOrder_id());

        renderSuccessJson(order.removeSystemInfo());
    }

    @ActionKey(Url.ORDER_SAVE)
    public void save() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_DELIVERY_NAME, Order.ORDER_DELIVERY_PHONE, Order.ORDER_DELIVERY_ADDRESS, Order.ORDER_MESSAGE, Order.ORDER_PAY_TYPE);

        validate("open_id", "pay_type");

        Member member = memberService.findByUser_id(request_user_id);

        JSONObject jsonObject = getAttr(Constant.REQUEST_PARAMETER);
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

        String order_id = Util.getRandomUUID();
        String member_id = member.getMember_id();
        String member_level_id = member.getMember_level_id();
        String member_level_name = "";
        Integer member_level_value = 0;
        if (!Util.isNullOrEmpty(member_level_id)) {
            MemberLevel memberLevel = memberLevelService.find(member_level_id);
            if (memberLevel != null) {
                member_level_name = memberLevel.getMember_level_name();
                member_level_value = memberLevel.getMember_level_value();
            }
        }

        for (int i = 0; i < productListJSONArray.size(); i++) {
            JSONObject productJSONObject = productListJSONArray.getJSONObject(i);

            String sku_id = productJSONObject.getString(Sku.SKU_ID);
            Integer product_quantity = productJSONObject.getInteger(Product.PRODUCT_QUANTITY);

            Sku sku = skuService.find(sku_id);
//            Product product = productCache.find(sku.getProduct_id());
//
//            if (product_quantity > sku.getProduct_stock()) {
//                throw new RuntimeException("库存不足:" + product.getProduct_name());
//            }

            //更新订单商品数量
            order_product_quantity += product_quantity;

            //更新商品应付价格
            JSONObject productPriceJSONObject = skuService.findProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);
            order_product_amount = order_product_amount.add(product_price.multiply(BigDecimal.valueOf(product_quantity)));
        }

        orderProductService.save(productListJSONArray, order_id, member_id, member.getParent_path(), member_level_id, request_user_id);

        Map<String, String> result = orderService.save(order_id, member_id, member_level_id, member_level_name, member_level_value, model.getOrder_delivery_name(), model.getOrder_delivery_phone(), model.getOrder_delivery_address(), model.getOrder_message(), model.getOrder_pay_type(), order_product_quantity, order_product_amount, order_freight_amount, order_discount_amount, open_id, pay_type, request_user_id);

        renderSuccessJson(result);
    }

    @ActionKey(Url.ORDER_DELETE)
    public void delete() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        orderService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ORDER_CHECK)
    public void check() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        validate(Product.PRODUCT_LIST);

        Map<String, Object> resultMap = orderService.check(request_user_id);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.ORDER_CONFIRM)
    public void confirm() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        Order order = orderService.confirm(model.getOrder_id(), request_user_id);

        renderSuccessJson(order.keep(Order.ORDER_NUMBER, Order.ORDER_IS_PAY, Order.ORDER_AMOUNT));
    }

    @ActionKey(Url.ORDER_PAY)
    public void pay() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        validate("open_id", "pay_type");

        JSONObject jsonObject = getAttr(Constant.REQUEST_PARAMETER);
        String open_id = jsonObject.getString("open_id");
        String pay_type = jsonObject.getString("pay_type");

        Map<String, String> result = orderService.pay(model.getOrder_id(), open_id, pay_type, request_user_id);

        renderSuccessJson(result);
    }

}