package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Product;

public class ProductCache extends Cache {

    private final String PRODUCT_OBJECT_CACHE = "product_object_cache";

    public Product getProductByProduct_id(String product_id) {
        return (Product) getObjectBykeyAndId(PRODUCT_OBJECT_CACHE, product_id);
    }

    public void setProductByProduct_id(Product product, String product_id) {
        setObjectBykeyAndId(product, PRODUCT_OBJECT_CACHE, product_id);
    }

    public void removeProductByProduct_id(String product_id) {
        removeObjectBykeyAndId(PRODUCT_OBJECT_CACHE, product_id);
    }

    public void removeProduct() {
        removeObjectByKey(PRODUCT_OBJECT_CACHE);
    }

}
