package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Product;

public class ProductCache extends Cache {

    private final String PRODUCT_CACHE_MODEL = "product_cache_model";

    public Product getProductByProduct_id(String product_id) {
        return (Product) ehcacheObject.get(PRODUCT_CACHE_MODEL + "_" + product_id);
    }

    public void setProductByProduct_id(Product product, String product_id) {
        ehcacheObject.put(PRODUCT_CACHE_MODEL + "_" + product_id, product);

        setMapByKeyAndId(PRODUCT_CACHE_MODEL, product_id);
    }

    public void removeProductByProduct_id(String product_id) {
        ehcacheObject.remove(PRODUCT_CACHE_MODEL + "_" + product_id);

        removeMapByKeyAndId(PRODUCT_CACHE_MODEL, product_id);
    }

    public void removeProduct() {
        ehcacheObject.removeAll(getMapByKey(PRODUCT_CACHE_MODEL));

        removeMapByKey(PRODUCT_CACHE_MODEL);
    }

}
