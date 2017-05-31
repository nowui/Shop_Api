package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.cache.FileCache;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.shop.cache.BrandCache;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class BrandService extends Service {

    private final BrandCache brandCache = new BrandCache();
    private final FileCache fileCache = new FileCache();

    public int count(String brand_name) {
        return brandCache.count(brand_name);
    }

    public List<Brand> list(String brand_name, int m, int n) {
        return brandCache.list(brand_name, m, n);
    }

    public Brand find(String brand_id) {
        Brand brand = brandCache.find(brand_id);

        if (Util.isNullOrEmpty(brand.getBrand_image())) {
            brand.put(Brand.BRAND_IMAGE_FILE, "");
        } else {
            File productImageFile = fileCache.find(brand.getBrand_image());
            brand.put(Brand.BRAND_IMAGE_FILE, productImageFile.keep(File.FILE_ID, File.FILE_NAME, File.FILE_PATH));
        }

        return brand;
    }

    public Brand save(Brand brand, String request_user_id) {
        return brandCache.save(brand, request_user_id);
    }

    public boolean update(Brand brand, String request_user_id) {
        return brandCache.update(brand, request_user_id);
    }

    public boolean delete(Brand brand, String request_user_id) {
        return brandCache.delete(brand.getBrand_id(), request_user_id);
    }

}