package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.OrderDao;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderCache extends Cache {

    private final String ORDER_COUNT_BY_MEMBER_ID_AND_ORDER_FLOW_CACHE = "order_count_by_member_id_and order_flow_cache";
    private final String ORDER_NUMBER_LIST_BY_DAY_CACHE = "order_number_list_by_day_cache";
    private final String ORDER_LIST_BY_USER_ID_CACHE = "order_list_by_user_id_cache";
    private final String ORDER_BY_ORDER_NUMBER_CACHE = "order_by_order_number_cache";
    private final String ORDER_BY_ORDER_ID_CACHE = "order_by_order_id_cache";

    private OrderDao orderDao = new OrderDao();

    public int count(String order_number, String order_flow) {
        return orderDao.count(order_number, order_flow);
    }

    public int countByUser_idAndOrder_flow(String user_id, String order_flow) {
        Integer count = CacheUtil.get(ORDER_COUNT_BY_MEMBER_ID_AND_ORDER_FLOW_CACHE, user_id + "_" + order_flow);

        if (count == null) {
            count = orderDao.countByUser_idAndOrder_flow(user_id, order_flow);

            CacheUtil.put(ORDER_COUNT_BY_MEMBER_ID_AND_ORDER_FLOW_CACHE, user_id + "_" + order_flow, count);
        }

        return count;
    }

    public void deleteCountByUser_idAndOrder_flow(String user_id, String order_flow) {
        CacheUtil.remove(ORDER_COUNT_BY_MEMBER_ID_AND_ORDER_FLOW_CACHE, user_id + "_" + order_flow);
    }

    public List<Order> list(String order_number, String order_flow, Integer m, Integer n) {
        return orderDao.list(order_number, order_flow, m, n);
    }

    public List<Order> listByUser_id(String user_id) {
        List<Order> orderList = CacheUtil.get(ORDER_LIST_BY_USER_ID_CACHE, user_id);

        if (orderList == null) {
            orderList = orderDao.listByUser_id(user_id);

            CacheUtil.put(ORDER_LIST_BY_USER_ID_CACHE, user_id, orderList);
        }

        return orderList;
    }

    public List<String> listOrderNumber(String day) {
        List<String> resultList = CacheUtil.get(ORDER_NUMBER_LIST_BY_DAY_CACHE, day);

        if (resultList == null) {
            resultList = orderDao.listOrderNumber(day);

            CacheUtil.put(ORDER_NUMBER_LIST_BY_DAY_CACHE, day, resultList);
        }

        return orderDao.listOrderNumber(day);
    }

    public void addOrderNumber(String order_number, String day) {
        List<String> resultList = listOrderNumber(day);

        resultList.add(order_number);

        CacheUtil.put(ORDER_NUMBER_LIST_BY_DAY_CACHE, day, resultList);
    }

    public Order find(String order_id) {
        Order order = CacheUtil.get(ORDER_BY_ORDER_ID_CACHE, order_id);

        if (order == null) {
            order = orderDao.find(order_id);

            CacheUtil.put(ORDER_BY_ORDER_ID_CACHE, order_id, order);
        }

        return order;
    }

    private String getOrder_number(String day) {
        return "E" + day + Util.getFixLenthString(6);
    }

    public Order findByOrder_number(String order_number) {
        Order order = CacheUtil.get(ORDER_BY_ORDER_NUMBER_CACHE, order_number);

        if (order == null) {
            order = orderDao.findByOrder_number(order_number);

            CacheUtil.put(ORDER_BY_ORDER_NUMBER_CACHE, order_number, order);
        }
        return order;
    }

    public Order save(String order_id, String member_id, String member_level_id, String member_level_name, Integer member_level_value, Integer order_product_quantity, BigDecimal order_product_amount, BigDecimal order_freight_amount, BigDecimal order_discount_amount, String request_user_id) {
        Order order = new Order();
        order.setOrder_id(order_id);

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
        order.setOrder_number(order_number);
        order.setMember_id(member_id);
        order.setMember_level_id(member_level_id);
        order.setMember_level_name(member_level_name);
        order.setMember_level_value(member_level_value);
        order.setOrder_product_quantity(order_product_quantity);
        order.setOrder_product_amount(order_product_amount);
        order.setOrder_freight_amount(order_freight_amount);
        order.setOrder_discount_amount(order_discount_amount);
        order.setOrder_amount(order_product_amount.subtract(order_freight_amount).subtract(order_discount_amount));
        order.setOrder_flow(OrderFlowEnum.WAIT_PAY.getKey());
        order.setOrder_status(false);

        CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());

        Order result = orderDao.save(order, request_user_id);

        CacheUtil.put(ORDER_BY_ORDER_NUMBER_CACHE, order_number, result);

        return result;
    }

    public boolean update(String order_id, BigDecimal order_amount, String order_pay_type, String order_pay_number, String order_pay_account, String order_pay_time, String order_pay_result, String order_flow, Boolean order_status) {
        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        return orderDao.update(order_id, order_amount, order_pay_type, order_pay_number, order_pay_account, order_pay_time, order_pay_result, order_flow, order_status);
    }

    public boolean updateByOrder_idAndOrder_is_confirm(String order_id) {
        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        return orderDao.updateByOrder_idAndOrder_is_confirm(order_id);
    }

    public boolean updateReceive(String order_id, String request_user_id) {
        Order order = find(order_id);

        if (order.getOrder_flow().equals(OrderFlowEnum.WAIT_SEND.getKey())) {
            CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

            return orderDao.updateReceive(order_id, request_user_id);
        }

        return false;
    }

    public void updateFinish(List<String> orderIdList) {
        for (String order_id : orderIdList) {
            CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);
        }

        orderDao.updateFinish(orderIdList);
    }

    public boolean delete(String order_id, String request_user_id) {
        Order order = find(order_id);
        if (order != null) {
            CacheUtil.remove(ORDER_LIST_BY_USER_ID_CACHE, order.getUser_id());
        }

        CacheUtil.remove(ORDER_BY_ORDER_ID_CACHE, order_id);

        return orderDao.delete(order_id, request_user_id);
    }

}