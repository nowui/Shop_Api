package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends Dao {

    private final String ORDER_NUMBER_LIST_BY_DAY_CACHE = "order_number_list_by_day_cache";
    private final String ORDER_LIST_BY_USER_ID_CACHE = "order_list_by_user_id_cache";
    private final String ORDER_BY_ORDER_NUMBER_CACHE = "order_by_order_number_cache";
    private final String ORDER_BY_ORDER_ID_CACHE = "order_by_order_id_cache";

    public int count(String order_number) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, order_number);
        SqlPara sqlPara = Db.getSqlPara("order.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Order> list(String order_number, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, order_number);
        map.put(Order.M, m);
        map.put(Order.N, n);
        SqlPara sqlPara = Db.getSqlPara("order.list", map);

        return new Order().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Order> listByUser_id(String user_id) {
        List<Order> orderList = CacheUtil.get(ORDER_LIST_BY_USER_ID_CACHE, user_id);

        if (orderList == null) {
            Kv map = Kv.create();
            map.put(Order.USER_ID, user_id);
            map.put(Order.M, 0);
            map.put(Order.N, 0);
            SqlPara sqlPara = Db.getSqlPara("order.listByUser_id", map);

            orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());

            if (orderList.size() > 0) {
                CacheUtil.put(ORDER_LIST_BY_USER_ID_CACHE, user_id, orderList);
            }
        }

        return orderList;
    }

    public List<String> listOrderNumber(String day) {
        List<String> resultList = CacheUtil.get(ORDER_NUMBER_LIST_BY_DAY_CACHE, day);

        if (resultList == null) {
            Kv map = Kv.create();
            map.put(Order.ORDER_NUMBER, day);
            SqlPara sqlPara = Db.getSqlPara("order.listOrderNumber", map);

            List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());

            resultList = new ArrayList<>();

            if (orderList.size() > 0) {
                for(Order order : orderList) {
                    resultList.add(order.getOrder_number());
                }

                CacheUtil.put(ORDER_NUMBER_LIST_BY_DAY_CACHE, day, resultList);
            }
        }

        return resultList;
    }

    public void addOrderNumber(String order_number, String day) {
        List<String> resultList = listOrderNumber(day);

        resultList.add(order_number);

        CacheUtil.put(ORDER_NUMBER_LIST_BY_DAY_CACHE, day, resultList);
    }

    public Order find(String order_id) {
        Order order = CacheUtil.get(ORDER_BY_ORDER_ID_CACHE, order_id);

        if (order == null) {
            Kv map = Kv.create();
            map.put(Order.ORDER_ID, order_id);
            SqlPara sqlPara = Db.getSqlPara("order.find", map);

            List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
            if (orderList.size() == 0) {
                order = null;
            } else {
                order = orderList.get(0);

                CacheUtil.put(ORDER_BY_ORDER_ID_CACHE, order_id, order);
            }
        }

        return order;
    }

    private String getOrder_number(String day) {
        return "E" + day + Util.getFixLenthString(6);
    }

    public Order findByOrder_number(String order_number) {
        Order order = CacheUtil.get(ORDER_BY_ORDER_NUMBER_CACHE, order_number);

        if (order == null) {
            Kv map = Kv.create();
            map.put(Order.ORDER_NUMBER, order_number);
            SqlPara sqlPara = Db.getSqlPara("order.findByOrder_number", map);

            List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
            if (orderList.size() == 0) {
                order = null;
            } else {
                order = orderList.get(0);

                CacheUtil.put(ORDER_BY_ORDER_NUMBER_CACHE, order_number, order);
            }
        }

        return order;
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

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order.getUser_id());

        order.setOrder_id(Util.getRandomUUID());
        order.setUser_id(request_user_id);
        order.setOrder_number(order_number);
        order.setOrder_discount_amount(BigDecimal.valueOf(0));
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

        CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());

        CacheUtil.put(ORDER_BY_ORDER_NUMBER_CACHE, order_number, order);

        return order;
    }

    public boolean update(String order_id, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result, String order_flow, Boolean order_status) {
        try {
            order_pay_time = DateUtil.dateTimeFormat.format(DateUtil.df.parse(order_pay_time));
        } catch (ParseException e) {

        }

        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.ORDER_AMOUNT, order_amount);
        map.put(Order.ORDER_PAY_TYPE, order_pay_type);
        map.put(Order.ORDER_PAY_NUMBER, order_pay_number);
        map.put(Order.ORDER_PAY_ACCOUNT, order_pay_account);
        map.put(Order.ORDER_PAY_TIME, order_pay_time);
        map.put(Order.ORDER_PAY_RESULT, order_pay_result);
        map.put(Order.ORDER_FLOW, order_flow);
        map.put(Order.ORDER_STATUS, order_status);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.update", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String order_id, String request_user_id) {
        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByOrder_idAndOrder_is_confirm(String order_id) {
        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("order.updateByOrder_idAndOrder_is_confirm", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}