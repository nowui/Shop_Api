package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.cache.UserCache;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.cache.SupplierCache;
import com.shanghaichuangshi.shop.model.Supplier;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class SupplierService extends Service {

    private final SupplierCache supplierCache = new SupplierCache();

    private UserCache userCache = new UserCache();

    public int count(String supplier_name) {
        return supplierCache.count(supplier_name);
    }

    public List<Supplier> list(String supplier_name, int m, int n) {
        return supplierCache.list(supplier_name, m, n);
    }

    public Supplier find(String supplier_id) {
        return supplierCache.find(supplier_id);
    }

    public Supplier save(Supplier supplier, User user, String request_user_id) {
        String user_id = Util.getRandomUUID();
        supplier.setUser_id(user_id);

        supplierCache.save(supplier, request_user_id);

        userCache.saveByUser_idAndUser_accountAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_account(), user.getUser_password(), supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return supplier;
    }

    public boolean update(Supplier supplier, User user, String request_user_id) {
        boolean result = supplierCache.update(supplier, request_user_id);

        userCache.updateByObject_idAndUser_accountAndUser_type(supplier.getSupplier_id(), user.getUser_account(), UserType.SUPPLIER.getKey(), request_user_id);

        userCache.updateByObject_idAndUser_passwordAndUser_type(supplier.getSupplier_id(), user.getUser_password(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

    public boolean delete(Supplier supplier, String request_user_id) {
        boolean result = supplierCache.delete(supplier.getSupplier_id(), request_user_id);

        userCache.deleteByObject_idAndUser_type(supplier.getSupplier_id(), UserType.SUPPLIER.getKey(), request_user_id);

        return result;
    }

}