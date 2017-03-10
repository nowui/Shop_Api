package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.SkuDao;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class SkuService extends Service {

    private static final SkuDao skuDao = new SkuDao();

    public List<Sku> list(String product_id) {
        return skuDao.list(product_id);
    }

    public Sku find(String sku_id) {
        return skuDao.find(sku_id);
    }

    public void save(List<Sku> skuList, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.save(skuList, request_user_id);
        }
    }

    public void updateProduct_stock(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.updateProduct_stock(skuList, product_id, request_user_id);
        }
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.delete(skuList, product_id, request_user_id);
        }
    }

}