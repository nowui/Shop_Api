package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.OrderProductCache;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class OrderProductService extends Service {

    private final OrderProductCache orderProductCache = new OrderProductCache();

    public List<OrderProduct> listByOder_id(String order_id) {
        return orderProductCache.listByOder_id(order_id);
    }

    public List<OrderProduct> listByUser_id(String user_id) {
        return orderProductCache.listByUser_id(user_id);
    }

    public OrderProduct find(String order_product_id) {
        return orderProductCache.find(order_product_id);
    }

    public void save(List<OrderProduct> orderProductList, String request_user_id) {
        orderProductCache.save(orderProductList, request_user_id);
    }

//    public boolean update(OrderProduct order_product, String request_user_id) {
//        return orderProductCache.update(order_product, request_user_id);
//    }

    public boolean updateByOrder_idAndOrder_statusAndUser_id(String order_id, Boolean order_status, String user_id) {
        return orderProductCache.updateByOrder_idAndOrder_statusAndUser_id(order_id, order_status, user_id);
    }

//    public boolean delete(OrderProduct order_product, String request_user_id) {
//        return orderProductCache.delete(order_product.getOrder_product_id(), request_user_id);
//    }

}