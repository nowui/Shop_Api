package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.shop.cache.ExpressCache;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class ExpressService extends Service {

    private final ExpressCache expressCache = new ExpressCache();

    public int count(String order_id) {
        return expressCache.count(order_id);
    }

    public List<Express> list(String order_id, int m, int n) {
        List<Express> expressList = expressCache.list(order_id, m, n);

        List<Express> resultList = new ArrayList<Express>();
        for (Express express : expressList) {
            resultList.add(find(express.getExpress_id()));
        }

        return resultList;
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