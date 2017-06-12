package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.BillCache;
import com.shanghaichuangshi.shop.cache.MemberCache;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.type.BillTypeEnum;

import java.math.BigDecimal;
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

    public BigDecimal findBill_AmountByUser_idAndBill_type(String user_id, String bill_type) {
        return billCache.findBill_AmountByUser_idAndBill_type(user_id, bill_type);
    }

    public void save(List<Bill> billList, String request_user_id) {
        for (Bill bill : billList) {
            deleteBill_AmountByUser_idAndBill_type(bill.getUser_id(), BillTypeEnum.ORDER.getKey());
            deleteBill_AmountByUser_idAndBill_type(bill.getUser_id(), BillTypeEnum.COMMISSION.getKey());
        }

        billCache.save(billList, request_user_id);
    }

    public void deleteBill_AmountByUser_idAndBill_type(String user_id, String bill_type) {
        billCache.deleteBill_AmountByUser_idAndBill_type(user_id, bill_type);
    }

}