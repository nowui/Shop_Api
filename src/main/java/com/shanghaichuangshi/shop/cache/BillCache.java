package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.BillDao;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.util.CacheUtil;

import java.math.BigDecimal;
import java.util.List;

public class BillCache extends Cache {

    public static final String BILL_COUNT_BY_USER_ID_AND_BILL_TYPE_CACHE = "bill_count_by_user_id_and_bill_type_cache";
    public static final String BILL_BY_USER_ID_CACHE = "bill_by_user_id_cache";

    private BillDao billDao = new BillDao();

    public int count(String bill_name) {
        return billDao.count(bill_name);
    }

    public List<Bill> list(String bill_name, Integer m, Integer n) {
        return billDao.list(bill_name, m, n);
    }

    public List<Bill> listByUser_id(String user_id, Integer m, Integer n) {
        List<Bill> billList = CacheUtil.get(BILL_BY_USER_ID_CACHE, user_id);

        if (billList == null) {
            billList = billDao.listByUser_id(user_id);

            CacheUtil.put(BILL_BY_USER_ID_CACHE, user_id, billList);
        }

        return billList;
    }

    public Bill find(String bill_id) {
        return billDao.find(bill_id);
    }

    public BigDecimal findBill_AmountByUser_idAndBill_type(String user_id, String bill_type) {
        BigDecimal bill_amount = CacheUtil.get(BILL_COUNT_BY_USER_ID_AND_BILL_TYPE_CACHE, user_id + "_" + bill_type);

        if (bill_amount == null) {
            bill_amount = billDao.findBill_AmountByUser_idAndBill_type(user_id, bill_type);

            CacheUtil.put(BILL_COUNT_BY_USER_ID_AND_BILL_TYPE_CACHE, user_id + "_" + bill_type, bill_amount);
        }

        return bill_amount;
    }

    public void save(List<Bill> billList, String request_user_id) {
        for (Bill bill : billList) {
            CacheUtil.remove(BILL_BY_USER_ID_CACHE, bill.getUser_id());
        }

        billDao.save(billList, request_user_id);
    }

    public void deleteBill_AmountByUser_idAndBill_type(String user_id, String bill_type) {
        CacheUtil.remove(BILL_COUNT_BY_USER_ID_AND_BILL_TYPE_CACHE, user_id + "_" + bill_type);
    }

}