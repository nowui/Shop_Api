package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
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

    public List<Bill> listByUser_id(String user_id, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Bill.USER_ID, user_id);
        map.put(Bill.M, m);
        map.put(Bill.N, n);
        SqlPara sqlPara = Db.getSqlPara("bill.listByUser_id", map);

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

    public void save(List<Bill> billList, String request_user_id) {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("bill.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Bill bill : billList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(Util.getRandomUUID());
            objectList.add(bill.getUser_id());
            objectList.add(bill.getObject_id());
            objectList.add(bill.getBill_type());
            objectList.add(bill.getBill_image());
            objectList.add(bill.getBill_name());
            objectList.add(bill.getBill_amount());
            objectList.add(bill.getBill_is_income());
            objectList.add(bill.getBill_time());
            objectList.add(bill.getBill_flow());
            objectList.add(bill.getBill_status());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(true);
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("SKU保存不成功");
            }
        }
    }

//    public boolean update(Bill bill, String request_user_id) {
//        bill.remove(Bill.SYSTEM_CREATE_USER_ID);
//        bill.remove(Bill.SYSTEM_CREATE_TIME);
//        bill.setSystem_update_user_id(request_user_id);
//        bill.setSystem_update_time(new Date());
//        bill.remove(Bill.SYSTEM_STATUS);
//
//        return bill.update();
//    }

//    public boolean delete(String bill_id, String request_user_id) {
//        JMap map = JMap.create();
//        map.put(Bill.BILL_ID, bill_id);
//        map.put(Bill.SYSTEM_UPDATE_USER_ID, request_user_id);
//        map.put(Bill.SYSTEM_UPDATE_TIME, new Date());
//        SqlPara sqlPara = Db.getSqlPara("bill.delete", map);
//
//        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
//    }

}