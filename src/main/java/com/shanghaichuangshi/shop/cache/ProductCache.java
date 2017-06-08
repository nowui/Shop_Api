package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class ProductCache extends Cache {

    private final String PRODUCT_BY_PRODUCT_ID_CACHE = "product_by_product_id_cache";

    private ProductDao productDao = new ProductDao();

    public int count(String product_name, String category_id, String brand_id) {
        return productDao.count(product_name, category_id, brand_id);
    }

    public List<Product> list(String product_name, String category_id, String brand_id, Integer m, Integer n) {
        return productDao.list(product_name, category_id, brand_id, m, n);
    }

    public List<Product> listAll() {
        return productDao.listAll();
    }

    public List<Product> listAllHot() {
        return productDao.listAllHot();
    }

    public Product find(String product_id) {
        Product product = CacheUtil.get(PRODUCT_BY_PRODUCT_ID_CACHE, product_id);

        if (product == null) {
            product = productDao.find(product_id);

            CacheUtil.put(PRODUCT_BY_PRODUCT_ID_CACHE, product_id, product);
        }
        return product;
    }

    public Product save(Product product, String request_user_id) {
        return productDao.save(product, request_user_id);
    }

    public boolean update(Product product, String request_user_id) {
        CacheUtil.remove(PRODUCT_BY_PRODUCT_ID_CACHE, product.getProduct_id());

        return productDao.update(product, request_user_id);
    }

    public boolean delete(String product_id, String request_user_id) {
        CacheUtil.remove(PRODUCT_BY_PRODUCT_ID_CACHE, product_id);

        return productDao.delete(product_id, request_user_id);
    }

}