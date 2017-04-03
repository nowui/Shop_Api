package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.dao.SupplierDao;
import com.shanghaichuangshi.shop.model.Supplier;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class SupplierService extends Service {

    private final SupplierDao supplierDao = new SupplierDao();

    private UserService userService = new UserService();

    public int count(Supplier supplier) {
        return supplierDao.count(supplier.getSupplier_name());
    }

    public List<Supplier> list(Supplier supplier, int m, int n) {
        return supplierDao.list(supplier.getSupplier_name(), m, n);
    }

    public Supplier find(String supplier_id) {
        return supplierDao.find(supplier_id);
    }

    public Supplier save(Supplier supplier, User user, String request_user_id) {
        String user_id = Util.getRandomUUID();
        supplier.setUser_id(user_id);

        supplierDao.save(supplier, request_user_id);

        userService.saveByUser_idAndUser_accountAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_account(), user.getUser_password(), supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return supplier;
    }

    public boolean update(Supplier supplier, User user, String request_user_id) {
        boolean result = supplierDao.update(supplier, request_user_id);

        userService.updateByObject_idAndUser_accountAndUser_type(supplier.getSupplier_id(), user.getUser_account(), UserType.SUPPLIER.getKey(), request_user_id);

        userService.updateByObject_idAndUser_passwordAndUser_type(supplier.getSupplier_id(), user.getUser_password(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Supplier supplier, String request_user_id) {
        boolean result = supplierDao.delete(supplier.getSupplier_id(), request_user_id);

        userService.deleteByObject_idAndUser_type(supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

}