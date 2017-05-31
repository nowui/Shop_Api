package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.CommissionCache;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CommissionService extends Service {

    private final CommissionCache commissionCache = new CommissionCache();

    public List<Commission> list(String product_id) {
        return commissionCache.list(product_id);
    }

    public Commission find(String commission_id) {
        return commissionCache.find(commission_id);
    }

    public void save(List<Commission> commissionList, String product_id, String request_user_id) {
        commissionCache.save(commissionList, product_id, request_user_id);
    }

    public void delete(List<Commission> commissionList, String product_id, String request_user_id) {
        commissionCache.delete(commissionList, product_id, request_user_id);
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        return commissionCache.deleteByProduct_id(product_id, request_user_id);
    }

}