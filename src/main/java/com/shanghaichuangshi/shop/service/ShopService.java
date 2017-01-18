package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.dao.ShopDao;
import com.shanghaichuangshi.shop.model.Shop;

import java.util.List;

public class ShopService extends Service {

    private final ShopDao shopDao = new ShopDao();

    public int count(Shop shop) {
        return shopDao.count();
    }

    public List<Shop> list(Shop shop) {
        return shopDao.list(shop.getShop_name(), shop.getM(), shop.getN());
    }

    public Shop find(Shop shop) {
        return shopDao.find(shop.getShop_id());
    }

    public void save(Shop shop) {
        shopDao.save(shop);
    }

    public void update(Shop shop) {
        shopDao.update(shop);
    }

    public void delete(Shop shop) {
        shopDao.delete(shop);
    }

}