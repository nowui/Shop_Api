package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.DeliveryDao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class DeliveryService extends Service {

    private final DeliveryDao deliveryDao = new DeliveryDao();

    private final CategoryService categoryService = new CategoryService();

    public int count(Delivery delivery, String request_user_id) {
        return deliveryDao.count(delivery.getDelivery_name(), request_user_id);
    }

    public List<Delivery> list(Delivery delivery, String request_user_id, int m, int n) {
        return deliveryDao.list(delivery.getDelivery_name(), request_user_id, m, n);
    }

    public Delivery find(String delivery_id) {
        return deliveryDao.find(delivery_id);
    }

    public Delivery findDefaultByUser_id(String user_id) {
        return deliveryDao.findDefaultByUser_id(user_id);
    }

    public Delivery save(Delivery delivery, String request_user_id) {
        deliveryDao.updateIsDefault("", request_user_id);

        return deliveryDao.save(delivery, request_user_id);
    }

    public Delivery update(Delivery delivery, String request_user_id) {
        deliveryDao.updateIsDefault(delivery.getDelivery_id(), request_user_id);

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