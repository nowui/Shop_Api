package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.OrderProductDao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class OrderProductCache extends Cache {

    private final String ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE = "order_product_list_by_order_id_cache";
    private final String ORDER_PRODUCT_LIST_BY_USER_ID_CACHE = "order_product_list_by_user_id_cache";

    private OrderProductDao orderProductDao = new OrderProductDao();

    public List<OrderProduct> listByOder_id(String order_id) {
        List<OrderProduct> orderProductList = CacheUtil.get(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id);

        if (orderProductList == null) {
            orderProductList = orderProductDao.listByOder_id(order_id);

            CacheUtil.put(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id, orderProductList);
        }

        return orderProductList;
    }

    public List<OrderProduct> listByUser_id(String user_id) {
        List<OrderProduct> orderProductList = CacheUtil.get(ORDER_PRODUCT_LIST_BY_USER_ID_CACHE, user_id);

        if (orderProductList == null) {
            orderProductList = orderProductDao.listByUser_id(user_id);

            CacheUtil.put(ORDER_PRODUCT_LIST_BY_USER_ID_CACHE, user_id, orderProductList);
        }

        return orderProductList;
    }

    public OrderProduct find(String order_product_id) {
        return orderProductDao.find(order_product_id);
    }

    public void save(List<OrderProduct> orderProductList, String request_user_id) {
        CacheUtil.remove(ORDER_PRODUCT_LIST_BY_USER_ID_CACHE, request_user_id);

        orderProductDao.save(orderProductList, request_user_id);
    }

    public boolean updateByOrder_idAndOrder_statusAndUser_id(String order_id, Boolean order_status, String user_id) {
        CacheUtil.remove(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id);
        CacheUtil.remove(ORDER_PRODUCT_LIST_BY_USER_ID_CACHE, user_id);

        return orderProductDao.updateByOrder_idAndOrder_statusAndUser_id(order_id, order_status, user_id);
    }

}