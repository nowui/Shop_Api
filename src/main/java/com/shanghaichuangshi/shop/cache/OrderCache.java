package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Order;

import java.util.List;

public class OrderCache extends Cache {

    private final String ORDER_NUMBER_LIST_CACHE = "order_number_list_cache";
    private final String ORDER_OBJECT_CACHE = "order_object_cache";

    public List<String> getOrderNumberListByDay(String day) {
        return (List<String>) getObjectBykeyAndId(ORDER_NUMBER_LIST_CACHE, day);
    }

    public void setOrderNumberListByDay(List<String> orderNumberList, String day) {
        setObjectBykeyAndId(orderNumberList, ORDER_NUMBER_LIST_CACHE, day);
    }

    public void removeOrderNumberListByDay(String day) {
        removeObjectBykeyAndId(ORDER_NUMBER_LIST_CACHE, day);
    }

    public void removeOrderNumberList() {
        removeObjectByKey(ORDER_NUMBER_LIST_CACHE);
    }

    public Order getOrderByOrder_id(String order_id) {
        return (Order) getObjectBykeyAndId(ORDER_OBJECT_CACHE, order_id);
    }

    public void setOrderByOrder_id(Order order, String order_id) {
        setObjectBykeyAndId(order, ORDER_OBJECT_CACHE, order_id);
    }

    public void removeOrderByOrder_id(String order_id) {
        removeObjectBykeyAndId(ORDER_OBJECT_CACHE, order_id);
    }

    public void removeOrder() {
        removeObjectByKey(ORDER_OBJECT_CACHE);
    }

}
