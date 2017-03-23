package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Sku;

import java.util.List;

public class SkuCache extends Cache {

    private final String SKU_LIST_CACHE = "sku_list_cache";
    private final String SKU_OBJECT_CACHE = "sku_object_cache";

    public List<Sku> getSkuListByProduct_id(String product_id) {
        return (List<Sku>) getObjectBykeyAndId(SKU_LIST_CACHE, product_id);
    }

    public void setSkuListByProduct_id(List<Sku> skuList, String product_id) {
        setObjectBykeyAndId(skuList, SKU_LIST_CACHE, product_id);
    }

    public void removeSkuListByProduct_id(String product_id) {
        removeObjectBykeyAndId(SKU_LIST_CACHE, product_id);
    }

    public void removeSkuList() {
        removeObjectByKey(SKU_LIST_CACHE);
    }

    public Sku getSkuBySku_id(String sku_id) {
        return (Sku) getObjectBykeyAndId(SKU_OBJECT_CACHE, sku_id);
    }

    public void setSkuBySku_id(Sku sku, String sku_id) {
        setObjectBykeyAndId(sku, SKU_OBJECT_CACHE, sku_id);
    }

    public void removeSkuBySku_id(String sku_id) {
        removeObjectBykeyAndId(SKU_OBJECT_CACHE, sku_id);
    }

    public void removeSku() {
        removeObjectByKey(SKU_OBJECT_CACHE);
    }

}
