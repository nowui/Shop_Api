package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.dao.DeliveryDao;
import com.shanghaichuangshi.shop.model.Delivery;

import java.util.List;

public class DeliveryService extends Service {

    private final DeliveryDao deliveryDao = new DeliveryDao();

    public int count(Delivery delivery) {
        return deliveryDao.count();
    }

    public List<Delivery> list(Delivery delivery) {
        return deliveryDao.list(delivery.getDelivery_name(), delivery.getM(), delivery.getN());
    }

    public Delivery find(Delivery delivery) {
        return deliveryDao.find(delivery.getDelivery_id());
    }

    public void save(Delivery delivery) {
        deliveryDao.save(delivery);
    }

    public void update(Delivery delivery) {
        deliveryDao.update(delivery);
    }

    public void delete(Delivery delivery) {
        deliveryDao.delete(delivery);
    }

}