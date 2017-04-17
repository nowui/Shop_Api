package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.shop.dao.SkuDao;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class SkuService extends Service {

    private final SkuDao skuDao = new SkuDao();

    public List<Sku> list(String product_id) {
        return skuDao.list(product_id);
    }

    public Sku find(String sku_id) {
        return skuDao.find(sku_id);
    }

    public void save(List<Sku> skuList, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.save(skuList, request_user_id);
        }
    }

    public void updateProduct_stock(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.updateProduct_stock(skuList, product_id, request_user_id);
        }
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() > 0) {
            skuDao.delete(skuList, product_id, request_user_id);
        }
    }

    public JSONObject getProduct_price(Sku sku, String member_level_id) {
        JSONArray jsonArray = JSON.parseArray(sku.getProduct_price());

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString(MemberLevel.MEMBER_LEVEL_ID).equals(member_level_id)) {
                return jsonObject;
            }
        }

        //如果没有匹配的会员等级价格就设置为默认价格
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (Util.isNullOrEmpty(jsonObject.getString(MemberLevel.MEMBER_LEVEL_ID))) {
                return jsonObject;
            }
        }

        throw new RuntimeException("找不到价格");
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        return skuDao.deleteByProduct_id(product_id, request_user_id);
    }

}