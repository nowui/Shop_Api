package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.BillDao;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class BillService extends Service {

    private final BillDao billDao = new BillDao();

    public int count(Bill bill) {
        return billDao.count(bill.getBill_name());
    }

    public List<Bill> list(Bill bill, int m, int n) {
        return billDao.list(bill.getBill_name(), m, n);
    }

    public List<Bill> listByUser_id(String user_id, int m, int n) {
        return billDao.listByUser_id(user_id, m, n);
    }

    public Bill find(String bill_id) {
        return billDao.find(bill_id);
    }

    public void save(List<Bill> billList, String request_user_id) {
        billDao.save(billList, request_user_id);
    }

//    public boolean update(Bill bill, String request_user_id) {
//        return billDao.update(bill, request_user_id);
//    }

//    public boolean delete(Bill bill, String request_user_id) {
//        return billDao.delete(bill.getBill_id(), request_user_id);
//    }

}