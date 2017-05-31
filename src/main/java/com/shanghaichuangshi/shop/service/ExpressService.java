package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.ExpressCache;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class ExpressService extends Service {

    private final ExpressCache expressCache = new ExpressCache();

    public int count(String order_id) {
        return expressCache.count(order_id);
    }

    public List<Express> list(String order_id, int m, int n) {
        return expressCache.list(order_id, m, n);
    }

    public Express find(String express_id) {
        return expressCache.find(express_id);
    }

    public Express save(Express express, String request_user_id) {
        return expressCache.save(express, request_user_id);
    }

    public boolean update(Express express, String request_user_id) {
        return expressCache.update(express, request_user_id);
    }

    public boolean delete(Express express, String request_user_id) {
        return expressCache.delete(express.getExpress_id(), request_user_id);
    }

}