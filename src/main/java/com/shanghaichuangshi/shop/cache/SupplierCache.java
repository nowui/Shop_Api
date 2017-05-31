package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.SupplierDao;
import com.shanghaichuangshi.shop.model.Supplier;

import java.util.List;

public class SupplierCache extends Cache {

    private SupplierDao supplierDao = new SupplierDao();

    public int count(String supplier_name) {
        return supplierDao.count(supplier_name);
    }

    public List<Supplier> list(String supplier_name, Integer m, Integer n) {
        return supplierDao.list(supplier_name, m, n);
    }

    public Supplier find(String supplier_id) {
        return supplierDao.find(supplier_id);
    }

    public Supplier save(Supplier supplier, String request_user_id) {
        return supplierDao.save(supplier, request_user_id);
    }

    public boolean update(Supplier supplier, String request_user_id) {
        return supplierDao.update(supplier, request_user_id);
    }

    public boolean delete(String supplier_id, String request_user_id) {
        return supplierDao.delete(supplier_id, request_user_id);
    }

}