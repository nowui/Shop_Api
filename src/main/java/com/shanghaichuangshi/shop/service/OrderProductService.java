package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.OrderProductDao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class OrderProductService extends Service {

    private static final OrderProductDao orderProductDao = new OrderProductDao();

    public OrderProduct find(String order_product_id) {
        return orderProductDao.find(order_product_id);
    }

    public OrderProduct save(OrderProduct order_product, String request_user_id) {
        return orderProductDao.save(order_product, request_user_id);
    }

    public boolean update(OrderProduct order_product, String request_user_id) {
        return orderProductDao.update(order_product, request_user_id);
    }

    public boolean delete(OrderProduct order_product, String request_user_id) {
        return orderProductDao.delete(order_product.getOrder_product_id(), request_user_id);
    }

}