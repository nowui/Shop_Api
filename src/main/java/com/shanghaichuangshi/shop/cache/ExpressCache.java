package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.ExpressDao;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.*;

public class ExpressCache extends Cache {

    private final String EXPRESS_LIST_BY_ORDER_ID_CACHE = "express_list_by_order_id_cache";
    private final String EXPRESS_BY_EXPRESS_ID_CACHE = "express_by_express_id_cache";

    private ExpressDao expressDao = new ExpressDao();

    public int count(String express_number) {
        return expressDao.count(express_number);
    }

    public List<Express> list(String express_number, Integer m, Integer n) {
        return expressDao.list(express_number, m, n);
    }

    public List<Express> listByOrder_id(String order_id) {
        List<Express> expressList = CacheUtil.get(EXPRESS_LIST_BY_ORDER_ID_CACHE, order_id);

        if (expressList == null) {
            expressList = expressDao.listByOrder_id(order_id);

            CacheUtil.put(EXPRESS_LIST_BY_ORDER_ID_CACHE, order_id, expressList);
        }

        return expressList;
    }

    public Express find(String express_id) {
        Express express = CacheUtil.get(EXPRESS_BY_EXPRESS_ID_CACHE, express_id);

        if (express == null) {
            express = expressDao.find(express_id);

            CacheUtil.put(EXPRESS_BY_EXPRESS_ID_CACHE, express_id, express);
        }

        return express;
    }

    public Express save(String express_id, Express express, String request_user_id) {
        CacheUtil.remove(EXPRESS_LIST_BY_ORDER_ID_CACHE, express.getOrder_id());

        return expressDao.save(express_id, express, request_user_id);
    }

    public boolean update(Express express, String request_user_id) {
        CacheUtil.remove(EXPRESS_LIST_BY_ORDER_ID_CACHE, express.getOrder_id());

        CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express.getExpress_id());

        return expressDao.update(express, request_user_id);
    }

    public Boolean updateBusiness(List<Express> expressList) {
        for (Express express : expressList) {
            CacheUtil.remove(EXPRESS_LIST_BY_ORDER_ID_CACHE, express.getOrder_id());

            CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express.getExpress_id());
        }

        return expressDao.updateBusiness(expressList);
    }

    public boolean delete(String express_id, String request_user_id) {
        Express express = find(express_id);

        CacheUtil.remove(EXPRESS_LIST_BY_ORDER_ID_CACHE, express.getOrder_id());

        CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express_id);

        return expressDao.delete(express_id, request_user_id);
    }

}