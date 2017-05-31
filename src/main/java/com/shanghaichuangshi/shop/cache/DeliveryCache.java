package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.DeliveryDao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class DeliveryCache extends Cache {

    private final String DELIVERY_LIST_BY_USER_ID_CACHE = "delivery_list_by_user_id_cache";
    private final String DELIVERY_BY_DELIVERY_ID_CACHE = "delivery_by_delivery_id_cache";

    private DeliveryDao deliveryDao = new DeliveryDao();

    public int count(String delivery_name, String user_id) {
        return deliveryDao.count(delivery_name, user_id);
    }

    public List<Delivery> list(String delivery_name, Integer m, Integer n) {
        return deliveryDao.list(delivery_name, m, n);
    }

    public List<Delivery> listByUser_id(String user_id, Integer m, Integer n) {
        List<Delivery> deliveryList = CacheUtil.get(DELIVERY_LIST_BY_USER_ID_CACHE, user_id);

        if (deliveryList == null) {
            deliveryList = deliveryDao.listByUser_id(user_id);

            CacheUtil.put(DELIVERY_LIST_BY_USER_ID_CACHE, user_id, deliveryList);
        }

        return deliveryList;
    }

    public Delivery find(String delivery_id) {
        Delivery delivery = CacheUtil.get(DELIVERY_BY_DELIVERY_ID_CACHE, delivery_id);

        if (delivery == null) {
            delivery = deliveryDao.find(delivery_id);

            CacheUtil.put(DELIVERY_BY_DELIVERY_ID_CACHE, delivery_id, delivery);
        }

        return delivery;
    }

    public Delivery findDefaultByUser_id(String user_id) {
        return deliveryDao.findDefaultByUser_id(user_id);
    }

    public Delivery save(Delivery delivery, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_BY_USER_ID_CACHE, request_user_id);

        return deliveryDao.save(delivery, request_user_id);
    }

    public boolean update(Delivery delivery, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_BY_USER_ID_CACHE, request_user_id);
        CacheUtil.remove(DELIVERY_BY_DELIVERY_ID_CACHE, delivery.getDelivery_id());

        return deliveryDao.update(delivery, request_user_id);
    }

    public boolean updateIsDefault(String delivery_id, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_BY_USER_ID_CACHE, request_user_id);

        return deliveryDao.updateIsDefault(delivery_id, request_user_id);
    }

    public boolean delete(String delivery_id, String request_user_id) {
        CacheUtil.remove(DELIVERY_LIST_BY_USER_ID_CACHE, request_user_id);
        CacheUtil.remove(DELIVERY_BY_DELIVERY_ID_CACHE, delivery_id);

        return deliveryDao.delete(delivery_id, request_user_id);
    }

}