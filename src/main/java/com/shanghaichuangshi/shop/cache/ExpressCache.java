package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.ExpressDao;
import com.shanghaichuangshi.shop.model.Express;

import java.util.List;

public class ExpressCache extends Cache {

    private ExpressDao expressDao = new ExpressDao();

    public int count(String order_id) {
        return expressDao.count(order_id);
    }

    public List<Express> list(String order_id, Integer m, Integer n) {
        return expressDao.list(order_id, m, n);
    }

    public List<Express> listByOrder_id(String order_id) {
        return expressDao.list(order_id, 0, 0);
    }

    public Express find(String express_id) {
        return expressDao.find(express_id);
    }

    public Express save(Express express, String request_user_id) {
        return expressDao.save(express, request_user_id);
    }

    public boolean update(Express express, String request_user_id) {
        return expressDao.update(express, request_user_id);
    }

    public boolean delete(String express_id, String request_user_id) {
        return expressDao.delete(express_id, request_user_id);
    }

}