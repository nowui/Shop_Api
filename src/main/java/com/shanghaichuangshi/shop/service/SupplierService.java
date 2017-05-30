package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.dao.UserDao;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.dao.SupplierDao;
import com.shanghaichuangshi.shop.model.Supplier;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class SupplierService extends Service {

    private final SupplierDao supplierDao = new SupplierDao();

    private UserDao userDao = new UserDao();

    public int count(String supplier_name) {
        return supplierDao.count(supplier_name);
    }

    public List<Supplier> list(String supplier_name, int m, int n) {
        return supplierDao.list(supplier_name, m, n);
    }

    public Supplier find(String supplier_id) {
        return supplierDao.find(supplier_id);
    }

    public Supplier save(Supplier supplier, User user, String request_user_id) {
        String user_id = Util.getRandomUUID();
        supplier.setUser_id(user_id);

        supplierDao.save(supplier, request_user_id);

        userDao.saveByUser_idAndUser_accountAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_account(), user.getUser_password(), supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return supplier;
    }

    public boolean update(Supplier supplier, User user, String request_user_id) {
        boolean result = supplierDao.update(supplier, request_user_id);

        userDao.updateByObject_idAndUser_accountAndUser_type(supplier.getSupplier_id(), user.getUser_account(), UserType.SUPPLIER.getKey(), request_user_id);

        userDao.updateByObject_idAndUser_passwordAndUser_type(supplier.getSupplier_id(), user.getUser_password(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Supplier supplier, String request_user_id) {
        boolean result = supplierDao.delete(supplier.getSupplier_id(), request_user_id);

        userDao.deleteByObject_idAndUser_type(supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

}