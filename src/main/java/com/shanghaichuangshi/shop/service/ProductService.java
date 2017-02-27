package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.CategoryType;

import java.util.List;

public class ProductService extends Service {

    private static final ProductDao productDao = new ProductDao();

    private static final CategoryService categoryService = new CategoryService();

    public int count(Product product) {
        return productDao.count(product.getProduct_name());
    }

    public List<Product> list(Product product, int m, int n) {
        return productDao.list(product.getProduct_name(), m, n);
    }

    public List<Category> categoryList() {
        return categoryService.listByCategory_key(CategoryType.PRODUCT.getKey());
    }

    public Product find(String product_id) {
        return productDao.find(product_id);
    }

    public Product save(Product product, String request_user_id) {
        return productDao.save(product, request_user_id);
    }

    public boolean update(Product product, String request_user_id) {
        return productDao.update(product, request_user_id);
    }

    public boolean delete(Product product, String request_user_id) {
        return productDao.delete(product.getProduct_id(), request_user_id);
    }

}