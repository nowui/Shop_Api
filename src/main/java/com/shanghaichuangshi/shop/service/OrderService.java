package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.shop.dao.OrderDao;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;

import java.math.BigDecimal;
import java.util.List;

public class OrderService extends Service {

    private static final OrderDao orderDao = new OrderDao();

    private static final SkuService skuService = new SkuService();
    private static final ProductService productService = new ProductService();
    private static final MemberService memberService = new MemberService();
    private static final MemberLevelService memberLevelService = new MemberLevelService();

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

    public Order save(Order order, JSONObject jsonObject, String request_user_id) {
        JSONArray productJSONArray = jsonObject.getJSONArray(Product.PRODUCT_LIST);

        if (productJSONArray.size() == 0) {
            throw new RuntimeException("请选购商品");
        }

        Integer order_product_number = 0;
        BigDecimal order_receivable_amount = BigDecimal.valueOf(0);
        String member_level_id = memberService.findMember_lever_idByUser_id(request_user_id);
        MemberLevel memberLevel = memberLevelService.find(member_level_id);

        for (int i = 0; i < productJSONArray.size(); i++) {
            JSONObject productJSONObject = productJSONArray.getJSONObject(i);

            String sku_id = productJSONObject.getString(Sku.SKU_ID);
            Integer product_number = productJSONObject.getInteger(OrderProduct.PRODUCT_NUMBER);

            Sku sku = skuService.find(sku_id);

            if (sku == null) {
                throw new RuntimeException("SKU不存在:" + sku_id);
            }

            if (product_number > sku.getProduct_stock()) {
                Product product = productService.find(sku.getProduct_id(), request_user_id);

                throw new RuntimeException("库存不足:" + product.getProduct_name());
            }

            //更新订单商品数量
            order_product_number += product_number;

            //更新订单应付价格
            JSONObject productPriceJSONObject = skuService.getProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);

            order_receivable_amount = order_receivable_amount.add(product_price.multiply(BigDecimal.valueOf(product_number)));
        }

        order.setOrder_product_number(order_product_number);
        order.setOrder_receivable_amount(order_receivable_amount);
        order.setMember_level_id(memberLevel.getMember_level_id());
        order.setMember_level_name(memberLevel.getMember_level_name());
        order.setMember_level_value(memberLevel.getMember_level_value());

        return orderDao.save(order, request_user_id);
    }

    public boolean update(Order order, String request_user_id) {
        return orderDao.update(order, request_user_id);
    }

    public boolean delete(Order order, String request_user_id) {
        return orderDao.delete(order.getOrder_id(), request_user_id);
    }

}