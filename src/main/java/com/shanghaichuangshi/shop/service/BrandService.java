package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.BrandDao;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class BrandService extends Service {

    private final BrandDao brandDao = new BrandDao();

    public int count(Brand brand) {
        return brandDao.count(brand.getBrand_name());
    }

    public List<Brand> list(Brand brand, int m, int n) {
        return brandDao.list(brand.getBrand_name(), m, n);
    }

    public Brand find(String brand_id) {
        return brandDao.find(brand_id);
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