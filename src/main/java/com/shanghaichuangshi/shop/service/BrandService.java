package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.service.FileService;
import com.shanghaichuangshi.shop.dao.BrandDao;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class BrandService extends Service {

    private final BrandDao brandDao = new BrandDao();

    private final FileService fileService = new FileService();

    public int count(Brand brand) {
        return brandDao.count(brand.getBrand_name());
    }

    public List<Brand> list(Brand brand, int m, int n) {
        return brandDao.list(brand.getBrand_name(), m, n);
    }

    public Brand find(String brand_id) {
        Brand brand = brandDao.find(brand_id);

        if (Util.isNullOrEmpty(brand.getBrand_image())) {
            brand.put(Brand.BRAND_IMAGE_FILE, "");
        } else {
            File productImageFile = fileService.find(brand.getBrand_image());
            brand.put(Brand.BRAND_IMAGE_FILE, productImageFile.keep(File.FILE_ID, File.FILE_NAME, File.FILE_PATH));
        }

        return brand;
    }

    public Brand save(Brand brand, String request_user_id) {
        return brandDao.save(brand, request_user_id);
    }

    public boolean update(Brand brand, String request_user_id) {
        return brandDao.update(brand, request_user_id);
    }

    public boolean delete(Brand brand, String request_user_id) {
        return brandDao.delete(brand.getBrand_id(), request_user_id);
    }

}