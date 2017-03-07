package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.SkuDao;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class SkuService extends Service {

    private static final SkuDao skuDao = new SkuDao();

    public List<Sku> list(Sku sku) {
        return skuDao.list(sku.getProduct_id());
    }

    public Sku find(String sku_id) {
        return skuDao.find(sku_id);
    }

    public Sku save(Sku sku, String request_user_id) {
        return skuDao.save(sku, request_user_id);
    }

    public boolean update(Sku sku, String request_user_id) {
        return skuDao.update(sku, request_user_id);
    }

    public boolean delete(Sku sku, String request_user_id) {
        return skuDao.delete(sku.getSku_id(), request_user_id);
    }

}