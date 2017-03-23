package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Brand;

public class BrandCache extends Cache {

    private final String BRAND_OBJECT_CACHE = "brand_object_cache";

    public Brand getBrandByBrand_id(String brand_id) {
        return (Brand) getObjectBykeyAndId(BRAND_OBJECT_CACHE, brand_id);
    }

    public void setBrandByBrand_id(Brand brand, String brand_id) {
        setObjectBykeyAndId(brand, BRAND_OBJECT_CACHE, brand_id);
    }

    public void removeBrandByBrand_id(String brand_id) {
        removeObjectBykeyAndId(BRAND_OBJECT_CACHE, brand_id);
    }

    public void removeBrand() {
        removeObjectByKey(BRAND_OBJECT_CACHE);
    }

}
