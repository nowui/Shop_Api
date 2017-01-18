package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.dao.BrandDao;
import com.shanghaichuangshi.shop.model.Brand;

import java.util.List;

public class BrandService extends Service {

    private final BrandDao brandDao = new BrandDao();

    public int count(Brand brand) {
        return brandDao.count();
    }

    public List<Brand> list(Brand brand) {
        return brandDao.list(brand.getBrand_name(), brand.getM(), brand.getN());
    }

    public Brand find(Brand brand) {
        return brandDao.find(brand.getBrand_id());
    }

    public void save(Brand brand) {
        brandDao.save(brand);
    }

    public void update(Brand brand) {
        brandDao.update(brand);
    }

    public void delete(Brand brand) {
        brandDao.delete(brand);
    }

}