package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class BillDao extends Dao {

    public int count(String bill_name) {
        JMap map = JMap.create();
        map.put(Bill.BILL_NAME, bill_name);
        SqlPara sqlPara = Db.getSqlPara("bill.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Bill> list(String bill_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Bill.BILL_NAME, bill_name);
        map.put(Bill.M, m);
        map.put(Bill.N, n);
        SqlPara sqlPara = Db.getSqlPara("bill.list", map);

        return new Bill().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Bill find(String bill_id) {
        JMap map = JMap.create();
        map.put(Bill.BILL_ID, bill_id);
        SqlPara sqlPara = Db.getSqlPara("bill.find", map);

        List<Bill> billList = new Bill().find(sqlPara.getSql(), sqlPara.getPara());
        if (billList.size() == 0) {
            return null;
        } else {
            return billList.get(0);
        }
    }

    public Bill save(Bill bill, String request_user_id) {
        bill.setBill_id(Util.getRandomUUID());
        bill.setSystem_create_user_id(request_user_id);
        bill.setSystem_create_time(new Date());
        bill.setSystem_update_user_id(request_user_id);
        bill.setSystem_update_time(new Date());
        bill.setSystem_status(true);

        bill.save();

        return bill;
    }

    public boolean update(Bill bill, String request_user_id) {
        bill.remove(Bill.SYSTEM_CREATE_USER_ID);
        bill.remove(Bill.SYSTEM_CREATE_TIME);
        bill.setSystem_update_user_id(request_user_id);
        bill.setSystem_update_time(new Date());
        bill.remove(Bill.SYSTEM_STATUS);

        return bill.update();
    }

    public boolean delete(String bill_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Bill.BILL_ID, bill_id);
        map.put(Bill.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Bill.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("bill.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}