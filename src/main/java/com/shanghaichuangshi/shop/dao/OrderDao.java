package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.cache.OrderCache;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.type.OrderStatusEnum;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends Dao {

    private final OrderCache orderCache = new OrderCache();

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

    public List<Order> listByUser_id(String user_id, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Order.USER_ID, user_id);
        map.put(Order.M, m);
        map.put(Order.N, n);
        SqlPara sqlPara = Db.getSqlPara("order.listByUser_id", map);

        return new Order().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<String> listOrderNumber(String day) {
        List<String> resultList = orderCache.getOrderNumberListByDay(day);

        if (resultList == null) {
            JMap map = JMap.create();
            map.put(Order.ORDER_NUMBER, day);
            SqlPara sqlPara = Db.getSqlPara("order.listOrderNumber", map);

            List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());

            resultList = new ArrayList<>();

            if (orderList.size() > 0) {
                for(Order order : orderList) {
                    resultList.add(order.getOrder_number());
                }

                orderCache.setOrderNumberListByDay(resultList, day);
            }
        }

        return resultList;
    }

    public void addOrderNumber(String order_number, String day) {
        String today = DateUtil.getDateString(new Date()).replaceAll("-", "");

        List<String> resultList = listOrderNumber(day);

        resultList.add(order_number);

        orderCache.setOrderNumberListByDay(resultList, day);
    }

    public Order find(String order_id) {
        Order order = orderCache.getOrderByOrder_id(order_id);

        if (order == null) {
            JMap map = JMap.create();
            map.put(Order.ORDER_ID, order_id);
            SqlPara sqlPara = Db.getSqlPara("order.find", map);

            List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
            if (orderList.size() == 0) {
                order = null;
            } else {
                order = orderList.get(0);

                orderCache.setOrderByOrder_id(order, order_id);
            }
        }

        return order;
    }

    private String getOrder_number(String day) {
        return "E" + day + Util.getFixLenthString(6);
    }

    public Order save(Order order, String request_user_id) {
        String today = DateUtil.getDateString(new Date()).replaceAll("-", "");

        String order_number = getOrder_number(today);

        List orderNumberList = listOrderNumber(today);

        boolean isExit = true;

        while (isExit) {
            if (orderNumberList.contains(order_number)) {
                order_number = getOrder_number(today);

                addOrderNumber(order_number, today);
            } else {
                isExit = false;
            }
        }

        order.setOrder_id(Util.getRandomUUID());
        order.setUser_id(request_user_id);
        order.setOrder_number(order_number);
        order.setOrder_receive_amount(BigDecimal.valueOf(0));
        order.setOrder_is_confirm(false);
        order.setOrder_is_pay(false);
        order.setOrder_pay_type("");
        order.setOrder_pay_number("");
        order.setOrder_pay_account("");
        order.setOrder_pay_time("");
        order.setOrder_pay_result("");
        order.setSystem_create_user_id(request_user_id);
        order.setSystem_create_time(new Date());
        order.setSystem_update_user_id(request_user_id);
        order.setSystem_update_time(new Date());
        order.setSystem_status(true);

        order.save();

        return order;
    }

    public boolean update(Order order, String request_user_id) {
        orderCache.removeOrderByOrder_id(order.getOrder_id());

        order.remove(Order.SYSTEM_CREATE_USER_ID);
        order.remove(Order.SYSTEM_CREATE_TIME);
        order.setSystem_update_user_id(request_user_id);
        order.setSystem_update_time(new Date());
        order.remove(Order.SYSTEM_STATUS);

        return order.update();
    }

    public boolean updateByOrder_numberAndOrder_receive_amountAndOrder_pay_typeAndOrder_pay_numberAndOrder_pay_accountAndOrder_pay_timeAndOrder_pay_result(String order_number, BigDecimal order_receive_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result) {
        try {
            order_pay_time = DateUtil.dateTimeFormat.format(DateUtil.df.parse(order_pay_time));
        } catch (ParseException e) {

        }

        JMap map = JMap.create();
        map.put(Order.ORDER_NUMBER, order_number);
        map.put(Order.ORDER_RECEIVE_AMOUNT, order_receive_amount);
        map.put(Order.ORDER_PAY_TYPE, order_pay_type);
        map.put(Order.ORDER_PAY_NUMBER, order_pay_number);
        map.put(Order.ORDER_PAY_ACCOUNT, order_pay_account);
        map.put(Order.ORDER_PAY_TIME, order_pay_time);
        map.put(Order.ORDER_PAY_RESULT, order_pay_result);
        map.put(Order.ORDER_STATUS, OrderStatusEnum.PAYED.getKey());
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.updateByOrder_numberAndOrder_receive_amountAndOrder_pay_typeAndOrder_pay_numberAndOrder_pay_accountAndOrder_pay_timeAndOrder_pay_result", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String order_id, String request_user_id) {
        orderCache.removeOrderByOrder_id(order_id);

        JMap map = JMap.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByOrder_idAndOrder_is_confirm(String order_id) {
        JMap map = JMap.create();
        map.put(Order.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("order.updateByOrder_idAndOrder_is_confirm", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}