package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.model.Product;

import java.util.List;

public class ProductService extends Service {

    private final ProductDao productDao = new ProductDao();

    public int count(Product product) {
        return productDao.count();
    }

    public List<Product> list(Product product) {
        return productDao.list(product.getProduct_name(), product.getM(), product.getN());
    }

    public Product find(Product product) {
        return productDao.find(product.getProduct_id());
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }

}