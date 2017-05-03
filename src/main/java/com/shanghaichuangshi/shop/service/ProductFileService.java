package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.ProductFileDao;
import com.shanghaichuangshi.shop.model.ProductFile;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class ProductFileService extends Service {

    private final ProductFileDao productFileDao = new ProductFileDao();

    public List<ProductFile> list(String product_id) {
        return productFileDao.list(product_id);
    }

    public ProductFile find(String product_file_id) {
        return productFileDao.find(product_file_id);
    }

    public void save(List<ProductFile> productFileList, String request_user_id) {
        productFileDao.save(productFileList, request_user_id);
    }

    public void delete(List<ProductFile> productFileList, String product_id, String request_user_id) {
        productFileDao.delete(productFileList, product_id, request_user_id);
    }

}