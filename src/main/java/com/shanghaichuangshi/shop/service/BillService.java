package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.BillCache;
import com.shanghaichuangshi.shop.cache.MemberCache;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Member;

import java.util.List;

public class BillService extends Service {

    private final BillCache billCache = new BillCache();
    private final MemberCache memberCache = new MemberCache();

    public int count(String bill_name) {
        return billCache.count(bill_name);
    }

    public List<Bill> list(String bill_name, int m, int n) {
        return billCache.list(bill_name, m, n);
    }

    public List<Bill> listByUser_id(String user_id, int m, int n) {
        return billCache.listByUser_id(user_id, m, n);
    }

    public List<Bill> listByMember_id(String member_id, Integer m, Integer n) {
        Member member = memberCache.find(member_id);

        return billCache.listByUser_id(member.getUser_id(), m, n);
    }

    public Bill find(String bill_id) {
        return billCache.find(bill_id);
    }

    public void save(List<Bill> billList, String request_user_id) {
        billCache.save(billList, request_user_id);
    }

//    public boolean update(Bill bill, String request_user_id) {
//        return billCache.update(bill, request_user_id);
//    }

//    public boolean delete(Bill bill, String request_user_id) {
//        return billCache.delete(bill.getBill_id(), request_user_id);
//    }

}