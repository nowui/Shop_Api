package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.OrderDao;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class OrderService extends Service {

    private static final OrderDao orderDao = new OrderDao();

    public int count(Order order) {
        return orderDao.count(order.getOrder_number());
    }

    public List<Order> list(Order order, int m, int n) {
        return orderDao.list(order.getOrder_number(), m, n);
    }

    public Order find(String order_id) {
        return orderDao.find(order_id);
    }

    public Order save(Order order, String request_user_id) {
        return orderDao.save(order, request_user_id);
    }

    public boolean update(Order order, String request_user_id) {
        return orderDao.update(order, request_user_id);
    }

    public boolean delete(Order order, String request_user_id) {
        return orderDao.delete(order.getOrder_id(), request_user_id);
    }

}