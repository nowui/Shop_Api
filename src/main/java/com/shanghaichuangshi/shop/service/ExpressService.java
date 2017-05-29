package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.ExpressDao;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class ExpressService extends Service {

    private final ExpressDao expressDao = new ExpressDao();

    public int count(String order_id) {
        return expressDao.count(order_id);
    }

    public List<Express> list(String order_id, int m, int n) {
        return expressDao.list(order_id, m, n);
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

    public boolean delete(Express express, String request_user_id) {
        return expressDao.delete(express.getExpress_id(), request_user_id);
    }

}