package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class OrderDao extends Dao {

    public int count(String order_number) {
        JMap map = JMap.create();
        map.put(Order.ORDER_NUMBER, order_number);
        SqlPara sqlPara = Db.getSqlPara("order.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Order> list(String order_number, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Order.ORDER_NUMBER, order_number);
        map.put(Order.M, m);
        map.put(Order.N, n);
        SqlPara sqlPara = Db.getSqlPara("order.list", map);

        return new Order().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Order find(String order_id) {
        JMap map = JMap.create();
        map.put(Order.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("order.find", map);

        List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
        if (orderList.size() == 0) {
            return null;
        } else {
            return orderList.get(0);
        }
    }

    public Order save(Order order, String request_user_id) {
        order.setOrder_id(Util.getRandomUUID());
        order.setSystem_create_user_id(request_user_id);
        order.setSystem_create_time(new Date());
        order.setSystem_update_user_id(request_user_id);
        order.setSystem_update_time(new Date());
        order.setSystem_status(true);

        order.save();

        return order;
    }

    public boolean update(Order order, String request_user_id) {
        order.remove(Order.SYSTEM_CREATE_USER_ID);
        order.remove(Order.SYSTEM_CREATE_TIME);
        order.setSystem_update_user_id(request_user_id);
        order.setSystem_update_time(new Date());
        order.remove(Order.SYSTEM_STATUS);

        return order.update();
    }

    public boolean delete(String order_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}