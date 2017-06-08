package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends Dao {

    public int count(String order_number, String order_flow) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, order_number);
        map.put(Order.ORDER_FLOW, order_flow);
        SqlPara sqlPara = Db.getSqlPara("order.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Order> list(String order_number, String order_flow, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, order_number);
        map.put(Order.ORDER_FLOW, order_flow);
        map.put(Order.M, m);
        map.put(Order.N, n);
        SqlPara sqlPara = Db.getSqlPara("order.list", map);

        return new Order().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Order> listByUser_id(String user_id) {
        Kv map = Kv.create();
        map.put(Order.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("order.listByUser_id", map);

        return new Order().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<String> listOrderNumber(String day) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, day);
        SqlPara sqlPara = Db.getSqlPara("order.listOrderNumber", map);

        List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());

        List<String> resultList = new ArrayList<String>();

        if (orderList.size() > 0) {
            for (Order order : orderList) {
                resultList.add(order.getOrder_number());
            }
        }

        return resultList;
    }

    public Order find(String order_id) {
        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("order.find", map);

        List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
        if (orderList.size() == 0) {
            return null;
        } else {
            return orderList.get(0);
        }
    }

    public Order findByOrder_number(String order_number) {
        Kv map = Kv.create();
        map.put(Order.ORDER_NUMBER, order_number);
        SqlPara sqlPara = Db.getSqlPara("order.findByOrder_number", map);

        List<Order> orderList = new Order().find(sqlPara.getSql(), sqlPara.getPara());
        if (orderList.size() == 0) {
            return null;
        } else {
            return orderList.get(0);
        }
    }

    public Order save(Order order, String request_user_id) {
        order.setUser_id(request_user_id);
        order.setOrder_number(order.getOrder_number());
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

        return order;
    }

    public boolean update(String order_id, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result, String order_flow, Boolean order_status) {
        try {
            order_pay_time = DateUtil.dateTimeFormat.format(DateUtil.df.parse(order_pay_time));
        } catch (ParseException e) {

        }

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

    public boolean updateByOrder_idAndOrder_is_confirm(String order_id) {
        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        SqlPara sqlPara = Db.getSqlPara("order.updateByOrder_idAndOrder_is_confirm", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateReceive(String order_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.ORDER_FLOW, OrderFlowEnum.WAIT_RECEIVE.getKey());
        map.put(Order.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.updateReceive", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public void updateFinish(List<String> orderIdList) {
        if (orderIdList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("order.updateFinish", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(String order_id : orderIdList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(OrderFlowEnum.FINISH.getKey());
            objectList.add(order_id);
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("订单更新不成功");
            }
        }
    }

    public boolean delete(String order_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Order.ORDER_ID, order_id);
        map.put(Order.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Order.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}