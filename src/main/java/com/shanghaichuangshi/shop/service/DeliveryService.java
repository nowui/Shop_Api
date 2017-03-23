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
        Category province = categoryService.find(delivery.getDelivery_province());
        Category city = categoryService.find(delivery.getDelivery_city());
        Category area = categoryService.find(delivery.getDelivery_area());

        delivery.setDelivery_address(province.getCategory_name() + city.getCategory_name() + area.getCategory_name() + delivery.getDelivery_street());

        deliveryDao.updateIsDefault("", request_user_id);

        return deliveryDao.save(delivery, request_user_id);
    }

    public boolean update(Delivery delivery, String request_user_id) {
        Category province = categoryService.find(delivery.getDelivery_province());
        Category city = categoryService.find(delivery.getDelivery_city());
        Category area = categoryService.find(delivery.getDelivery_area());

        delivery.setDelivery_address(province.getCategory_name() + city.getCategory_name() + area.getCategory_name() + delivery.getDelivery_street());

        deliveryDao.updateIsDefault(delivery.getDelivery_id(), request_user_id);

        return deliveryDao.update(delivery, request_user_id);
    }

    public boolean delete(Delivery delivery, String request_user_id) {
        return deliveryDao.delete(delivery.getDelivery_id(), request_user_id);
    }

}