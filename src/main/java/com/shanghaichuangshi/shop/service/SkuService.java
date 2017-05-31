package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.SkuCache;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class SkuService extends Service {

    private final SkuCache skuCache = new SkuCache();

    public List<Sku> list(String product_id) {
        return skuCache.list(product_id);
    }

    public Sku find(String sku_id) {
        return skuCache.find(sku_id);
    }

    public void save(List<Sku> skuList, String request_user_id) {
        skuCache.save(skuList, request_user_id);
    }

    public void updateProduct_stock(List<Sku> skuList, String product_id, String request_user_id) {
        skuCache.updateProduct_stock(skuList, product_id, request_user_id);
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        skuCache.delete(skuList, product_id, request_user_id);
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        return skuCache.deleteByProduct_id(product_id, request_user_id);
    }

}