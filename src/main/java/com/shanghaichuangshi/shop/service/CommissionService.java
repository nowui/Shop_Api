package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.CommissionDao;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CommissionService extends Service {

    private final CommissionDao commissionDao = new CommissionDao();

    public List<Commission> list(String product_id) {
        return commissionDao.list(product_id);
    }

    public Commission find(String commission_id) {
        return commissionDao.find(commission_id);
    }

    public void save(List<Commission> commissionList, String request_user_id) {
        commissionDao.save(commissionList, request_user_id);
    }

    public void delete(List<Commission> commissionList, String product_id, String request_user_id) {
        commissionDao.delete(commissionList, product_id, request_user_id);
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        return commissionDao.deleteByProduct_id(product_id, request_user_id);
    }

}