package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.DeliveryCache;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class DeliveryService extends Service {

    private final DeliveryCache deliveryCache = new DeliveryCache();

    public int count(String delivery_name, String request_user_id) {
        return deliveryCache.count(delivery_name, request_user_id);
    }

    public List<Delivery> list(String delivery_name, int m, int n) {
        return deliveryCache.list(delivery_name, m, n);
    }

    public List<Delivery> listByUser_id(String user_id, Integer m, Integer n) {
        return deliveryCache.listByUser_id(user_id, m, n);
    }

    public Delivery find(String delivery_id) {
        return deliveryCache.find(delivery_id);
    }

    public Delivery findDefaultByUser_id(String user_id) {
        return deliveryCache.findDefaultByUser_id(user_id);
    }

    public Delivery save(Delivery delivery, String request_user_id) {
        if (delivery.getDelivery_is_default()) {
            deliveryCache.updateIsDefault("", request_user_id);
        } else {
            List<Delivery> deliveryList = deliveryCache.listByUser_id(request_user_id, 0, 0);

            if (deliveryList.size() == 0) {
                delivery.setDelivery_is_default(true);
            }
        }

//        delivery.setDelivery_address(delivery.getDelivery_province() + delivery.getDelivery_city() + delivery.getDelivery_area() + delivery.getDelivery_street());

        return deliveryCache.save(delivery, request_user_id);
    }

    public Delivery update(Delivery delivery, String request_user_id) {
        if (delivery.getDelivery_is_default()) {
            deliveryCache.updateIsDefault(delivery.getDelivery_id(), request_user_id);
        }

//        delivery.setDelivery_address(delivery.getDelivery_province() + delivery.getDelivery_city() + delivery.getDelivery_area() + delivery.getDelivery_street());

        deliveryCache.update(delivery, request_user_id);

        return delivery;
    }

    public Delivery delete(Delivery delivery, String request_user_id) {
        deliveryCache.delete(delivery.getDelivery_id(), request_user_id);

        Delivery d = findDefaultByUser_id(request_user_id);

        if (d == null) {
            d = new Delivery();
        }

        return d;
    }

}