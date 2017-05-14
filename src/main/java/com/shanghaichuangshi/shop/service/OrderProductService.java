package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.OrderProductDao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class OrderProductService extends Service {

    private final OrderProductDao orderProductDao = new OrderProductDao();

    public List<OrderProduct> listByOder_id(String order_id) {
        return orderProductDao.listByOder_id(order_id);
    }

    public List<OrderProduct> listByUser_id(String user_id) {
        return orderProductDao.listByUser_id(user_id);
    }

    public OrderProduct find(String order_product_id) {
        return orderProductDao.find(order_product_id);
    }

    public void save(List<OrderProduct> orderProductList, String request_user_id) {
        orderProductDao.save(orderProductList, request_user_id);
    }

//    public boolean update(OrderProduct order_product, String request_user_id) {
//        return orderProductDao.update(order_product, request_user_id);
//    }

    public boolean updateByOrder_idAndOrder_statusAndUser_id(String order_id, Boolean order_status, String user_id) {
        return orderProductDao.updateByOrder_idAndOrder_statusAndUser_id(order_id, order_status, user_id);
    }

//    public boolean delete(OrderProduct order_product, String request_user_id) {
//        return orderProductDao.delete(order_product.getOrder_product_id(), request_user_id);
//    }

}