package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.BrandDao;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class BrandCache extends Cache {

    private final String BRAND_BY_BRAND_ID_CACHE = "brand_by_brand_id_cache";

    private BrandDao brandDao = new BrandDao();

    public int count(String brand_name) {
        return brandDao.count(brand_name);
    }

    public List<Brand> list(String brand_name, Integer m, Integer n) {
        return brandDao.list(brand_name, m, n);
    }

    public Brand find(String brand_id) {
        Brand brand = CacheUtil.get(BRAND_BY_BRAND_ID_CACHE, brand_id);

        if (brand == null) {
            brand = brandDao.find(brand_id);

            CacheUtil.put(BRAND_BY_BRAND_ID_CACHE, brand_id, brand);
        }

        return brand;
    }

    public Brand save(Brand brand, String request_user_id) {
        return brandDao.save(brand, request_user_id);
    }

    public boolean update(Brand brand, String request_user_id) {
        CacheUtil.remove(BRAND_BY_BRAND_ID_CACHE, brand.getBrand_id());

        return brandDao.update(brand, request_user_id);
    }

    public boolean delete(String brand_id, String request_user_id) {
        CacheUtil.remove(BRAND_BY_BRAND_ID_CACHE, brand_id);

        return brandDao.delete(brand_id, request_user_id);
    }

}