package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Brand;

public class BrandCache extends Cache {

    private final String BRAND_CACHE_MODEL = "brand_cache_model";

    public Brand getBrandByBrand_id(String brand_id) {
        return (Brand) ehcacheObject.get(BRAND_CACHE_MODEL + "_" + brand_id);
    }

    public void setBrandByBrand_id(Brand brand, String brand_id) {
        ehcacheObject.put(BRAND_CACHE_MODEL + "_" + brand_id, brand);

        setMapByKeyAndId(BRAND_CACHE_MODEL, brand_id);
    }

    public void removeBrandByBrand_id(String brand_id) {
        ehcacheObject.remove(BRAND_CACHE_MODEL + "_" + brand_id);

        removeMapByKeyAndId(BRAND_CACHE_MODEL, brand_id);
    }

    public void removeBrand() {
        ehcacheObject.removeAll(getMapByKey(BRAND_CACHE_MODEL));

        removeMapByKey(BRAND_CACHE_MODEL);
    }

}
