package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Supplier;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class SupplierDao extends Dao {

    public int count(String supplier_name) {
        Kv map = Kv.create();
        map.put(Supplier.SUPPLIER_NAME, supplier_name);
        SqlPara sqlPara = Db.getSqlPara("supplier.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Supplier> list(String supplier_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Supplier.SUPPLIER_NAME, supplier_name);
        map.put(Supplier.M, m);
        map.put(Supplier.N, n);
        SqlPara sqlPara = Db.getSqlPara("supplier.list", map);

        return new Supplier().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Supplier find(String supplier_id) {
        Kv map = Kv.create();
        map.put(Supplier.SUPPLIER_ID, supplier_id);
        SqlPara sqlPara = Db.getSqlPara("supplier.find", map);

        List<Supplier> supplierList = new Supplier().find(sqlPara.getSql(), sqlPara.getPara());
        if (supplierList.size() == 0) {
            return null;
        } else {
            return supplierList.get(0);
        }
    }

    public Supplier save(Supplier supplier, String request_user_id) {
        supplier.setSupplier_id(Util.getRandomUUID());
        supplier.setSystem_create_user_id(request_user_id);
        supplier.setSystem_create_time(new Date());
        supplier.setSystem_update_user_id(request_user_id);
        supplier.setSystem_update_time(new Date());
        supplier.setSystem_status(true);

        supplier.save();

        return supplier;
    }

    public boolean update(Supplier supplier, String request_user_id) {
        supplier.remove(Supplier.SYSTEM_CREATE_USER_ID);
        supplier.remove(Supplier.SYSTEM_CREATE_TIME);
        supplier.setSystem_update_user_id(request_user_id);
        supplier.setSystem_update_time(new Date());
        supplier.remove(Supplier.SYSTEM_STATUS);

        return supplier.update();
    }

//    public boolean updateBySupplier_idAndUser_id(String supplier_id, String user_id, String request_user_id) {
//        Kv map = Kv.create();
//        map.put(Supplier.SUPPLIER_ID, supplier_id);
//        map.put(Supplier.USER_ID, user_id);
//        map.put(Supplier.SYSTEM_UPDATE_USER_ID, request_user_id);
//        map.put(Supplier.SYSTEM_UPDATE_TIME, new Date());
//        SqlPara sqlPara = Db.getSqlPara("supplier.updateBySupplier_idAndUser_id", map);
//
//        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
//    }

    public boolean delete(String supplier_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Supplier.SUPPLIER_ID, supplier_id);
        map.put(Supplier.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Supplier.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("supplier.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}