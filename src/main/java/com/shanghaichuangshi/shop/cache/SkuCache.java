package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Sku;

import java.util.List;

public class SkuCache extends Cache {

    private final String SKU_CACHE_LIST = "sku_cache_list";
    private final String SKU_CACHE_MODEL = "sku_cache_model";

    public List<Sku> getSkuListByProduct_id(String product_id) {
        return ehcacheList.get(SKU_CACHE_LIST + "_" + product_id);
    }

    public void setSkuListByProduct_id(List<Sku> skuList, String product_id) {
        ehcacheList.put(SKU_CACHE_LIST + "_" + product_id, skuList);

        setMapByKeyAndId(SKU_CACHE_LIST, product_id);
    }

    public void removeSkuListByProduct_id(String product_id) {
        ehcacheList.remove(SKU_CACHE_LIST + "_" + product_id);

        removeMapByKeyAndId(SKU_CACHE_LIST, product_id);
    }

    public void removeSkuList() {
        ehcacheList.removeAll(getMapByKey(SKU_CACHE_LIST));

        removeMapByKey(SKU_CACHE_LIST);
    }

    public Sku getSkuBySku_id(String sku_id) {
        return (Sku) ehcacheObject.get(SKU_CACHE_MODEL + "_" + sku_id);
    }

    public void setSkuBySku_id(Sku sku, String sku_id) {
        ehcacheObject.put(SKU_CACHE_MODEL + "_" + sku_id, sku);

        setMapByKeyAndId(SKU_CACHE_MODEL, sku_id);
    }

    public void removeSkuBySku_id(String sku_id) {
        ehcacheObject.remove(SKU_CACHE_MODEL + "_" + sku_id);

        removeMapByKeyAndId(SKU_CACHE_MODEL, sku_id);
    }

    public void removeSku() {
        ehcacheObject.removeAll(getMapByKey(SKU_CACHE_MODEL));

        removeMapByKey(SKU_CACHE_MODEL);
    }

}
