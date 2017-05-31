package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.CommissionDao;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class CommissionCache extends Cache {

    private final String COMMISSION_LIST_BY_PRODUCT_ID_CACHE = "commission_list_by_product_id_cache";
    private final String COMMISSION_CACHE = "commission_cache";

    private CommissionDao commissionDao = new CommissionDao();

    public List<Commission> list(String product_id) {
        List<Commission> commissionList = CacheUtil.get(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id);

        if (commissionList == null) {
            commissionList = commissionDao.list(product_id);

            CacheUtil.put(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id, commissionList);
        }

        return commissionList;
    }

    public Commission find(String commission_id) {
        Commission commission = CacheUtil.get(COMMISSION_CACHE, commission_id);

        if (commission == null) {
            commission = commissionDao.find(commission_id);

            CacheUtil.put(COMMISSION_CACHE, commission_id, commission);
        }

        return commission;
    }

    public void save(List<Commission> commissionList, String product_id, String request_user_id) {
        if (commissionList.size() == 0) {
            return;
        }

        CacheUtil.remove(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id);

        commissionDao.save(commissionList, request_user_id);
    }

    public void delete(List<Commission> commissionList, String product_id, String request_user_id) {
        for(Commission commission : commissionList) {
            CacheUtil.remove(COMMISSION_CACHE, commission.getCommission_id());
        }

        CacheUtil.remove(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id);

        commissionDao.delete(commissionList, product_id, request_user_id);
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        List<Commission> commissionList = CacheUtil.get(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id);

        for(Commission commission : commissionList) {
            CacheUtil.remove(COMMISSION_CACHE, commission.getCommission_id());
        }

        CacheUtil.remove(COMMISSION_LIST_BY_PRODUCT_ID_CACHE, product_id);

        return commissionDao.deleteByProduct_id(product_id, request_user_id);
    }

}