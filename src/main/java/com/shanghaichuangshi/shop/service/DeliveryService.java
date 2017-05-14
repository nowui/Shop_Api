package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.DeliveryDao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class DeliveryService extends Service {

    private final DeliveryDao deliveryDao = new DeliveryDao();

    public int count(Delivery delivery, String request_user_id) {
        return deliveryDao.count(delivery.getDelivery_name(), request_user_id);
    }

    public List<Delivery> list(Delivery delivery, int m, int n) {
        return deliveryDao.list(delivery.getDelivery_name(), m, n);
    }

    public List<Delivery> listByUser_id(String user_id, Integer m, Integer n) {
        return deliveryDao.listByUser_id(user_id, m, n);
    }

    public Delivery find(String delivery_id) {
        return deliveryDao.find(delivery_id);
    }

    public Delivery findDefaultByUser_id(String user_id) {
        return deliveryDao.findDefaultByUser_id(user_id);
    }

    public Delivery save(Delivery delivery, String request_user_id) {
        if (delivery.getDelivery_is_default()) {
            deliveryDao.updateIsDefault("", request_user_id);
        } else {
            List<Delivery> deliveryList = deliveryDao.listByUser_id(request_user_id, 0, 0);

            if (deliveryList.size() == 0) {
                delivery.setDelivery_is_default(true);
            }
        }

//        delivery.setDelivery_address(delivery.getDelivery_province() + delivery.getDelivery_city() + delivery.getDelivery_area() + delivery.getDelivery_street());

        return deliveryDao.save(delivery, request_user_id);
    }

    public Delivery update(Delivery delivery, String request_user_id) {
        if (delivery.getDelivery_is_default()) {
            deliveryDao.updateIsDefault(delivery.getDelivery_id(), request_user_id);
        }

//        delivery.setDelivery_address(delivery.getDelivery_province() + delivery.getDelivery_city() + delivery.getDelivery_area() + delivery.getDelivery_street());

        deliveryDao.update(delivery, request_user_id);

        return delivery;
    }

    public Delivery delete(Delivery delivery, String request_user_id) {
        deliveryDao.delete(delivery.getDelivery_id(), request_user_id);

        Delivery d = findDefaultByUser_id(request_user_id);

        if (d == null) {
            d = new Delivery();
        }

        return d;
    }

}