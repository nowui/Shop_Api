package com.shanghaichuangshi.shop.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.ExpressDao;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class ExpressCache extends Cache {

    private final String EXPRESS_BY_EXPRESS_ID_CACHE = "express_id_express_id_cache";

    private ExpressDao expressDao = new ExpressDao();

    public int count(String express_number) {
        return expressDao.count(express_number);
    }

    public List<Express> list(String express_number, Integer m, Integer n) {
        return expressDao.list(express_number, m, n);
    }

    public List<Express> listByOrder_id(String order_id) {
        return expressDao.listByOrder_id(order_id);
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
        return expressDao.save(express_id, express, request_user_id);
    }

    public boolean update(Express express, String request_user_id) {
        CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express.getExpress_id());

        return expressDao.update(express, request_user_id);
    }

    public Boolean updateBusiness(List<Express> expressList) {
        for (Express express : expressList) {
            CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express.getExpress_id());
        }

        return expressDao.updateBusiness(expressList);
    }

    public boolean delete(String express_id, String request_user_id) {
        CacheUtil.remove(EXPRESS_BY_EXPRESS_ID_CACHE, express_id);

        return expressDao.delete(express_id, request_user_id);
    }

}