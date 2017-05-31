package com.shanghaichuangshi.shop.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkuDao extends Dao {

    public List<Sku> list(String product_id) {
        Kv map = Kv.create();
        map.put(Sku.PRODUCT_ID, product_id);
        SqlPara sqlPara = Db.getSqlPara("sku.list", map);

        return new Sku().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Sku> listByProduct_idAndMember_level_id(String product_id, String member_level_id) {
        List<Sku> skuList = list(product_id);

        for (Sku sku : skuList) {
            JSONArray priceArray = new JSONArray();

            JSONObject jsonObject = findProduct_price(sku, member_level_id);

            priceArray.add(jsonObject);

            sku.setProduct_price(priceArray.toJSONString());
        }

        return skuList;
    }

    public Sku find(String sku_id) {
        Kv map = Kv.create();
        map.put(Sku.SKU_ID, sku_id);
        SqlPara sqlPara = Db.getSqlPara("sku.find", map);

        List<Sku> skuList = new Sku().find(sqlPara.getSql(), sqlPara.getPara());
        if (skuList.size() == 0) {
            return null;
        } else {
            return skuList.get(0);
        }
    }

    public JSONObject findProduct_price(Sku sku, String member_level_id) {
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

    public void save(List<Sku> skuList, String request_user_id) {
        if (skuList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("sku.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Sku sku : skuList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(Util.getRandomUUID());
            objectList.add(sku.getProduct_id());
            objectList.add(sku.getProduct_attribute());
            objectList.add(sku.getProduct_price());
            objectList.add(sku.getProduct_stock());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(true);
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("SKU保存不成功");
            }
        }
    }

    public void updateProduct_stock(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("sku.updateProduct_stock", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Sku sku : skuList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(sku.getProduct_stock());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(sku.getSku_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("SKU库存更新不成功");
            }
        }
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        if (skuList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("sku.delete", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Sku sku : skuList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(sku.getSku_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("SKU删除不成功");
            }
        }
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Sku.PRODUCT_ID, product_id);
        map.put(Sku.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Sku.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("sku.deleteByProduct_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}