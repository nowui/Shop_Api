package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.BillDao;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Member;

import java.util.List;

public class BillService extends Service {

    private final BillDao billDao = new BillDao();

    private final MemberService memberService = new MemberService();

    public int count(String bill_name) {
        return billDao.count(bill_name);
    }

    public List<Bill> list(String bill_name, int m, int n) {
        return billDao.list(bill_name, m, n);
    }

    public List<Bill> listByUser_id(String user_id, int m, int n) {
        return billDao.listByUser_id(user_id, m, n);
    }

    public List<Bill> listByMember_id(String member_id, Integer m, Integer n) {
        Member member = memberService.find(member_id);

        return billDao.listByUser_id(member.getUser_id(), m, n);
    }

    public Bill find(String bill_id) {
        return billDao.find(bill_id);
    }

    public void save(List<Bill> billList, String request_user_id) {
        if (billList.size() > 0) {
            billDao.save(billList, request_user_id);
        }
    }

//    public boolean update(Bill bill, String request_user_id) {
//        return billDao.update(bill, request_user_id);
//    }

//    public boolean delete(Bill bill, String request_user_id) {
//        return billDao.delete(bill.getBill_id(), request_user_id);
//    }

}