package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.cache.SkuCache;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkuDao extends Dao {

    private static final SkuCache skuCache = new SkuCache();

    public List<Sku> list(String product_id) {
        List<Sku> skuList = skuCache.getSkuListByProduct_id(product_id);

        if (skuList == null) {
            JMap map = JMap.create();
            map.put(Sku.PRODUCT_ID, product_id);
            SqlPara sqlPara = Db.getSqlPara("sku.list", map);

            skuList = new Sku().find(sqlPara.getSql(), sqlPara.getPara());

            if (skuList != null) {
                skuCache.setSkuListByProduct_id(skuList, product_id);
            }
        }

        return skuList;
    }

    public Sku find(String sku_id) {
        Sku sku = skuCache.getSkuBySku_id(sku_id);

        if (sku == null) {
            JMap map = JMap.create();
            map.put(Sku.SKU_ID, sku_id);
            SqlPara sqlPara = Db.getSqlPara("sku.find", map);

            List<Sku> skuList = new Sku().find(sqlPara.getSql(), sqlPara.getPara());
            if (skuList.size() == 0) {
                sku = null;
            } else {
                sku = skuList.get(0);

                skuCache.setSkuBySku_id(sku, sku_id);
            }
        }

        return sku;
    }

    public void save(List<Sku> skuList, String request_user_id) {
        JMap map = JMap.create();
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
        for(Sku sku : skuList) {
            skuCache.removeSkuBySku_id(sku.getSku_id());
        }

        skuCache.removeSkuListByProduct_id(product_id);

        JMap map = JMap.create();
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
                throw new RuntimeException("SKU更新不成功");
            }
        }
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        for(Sku sku : skuList) {
            skuCache.removeSkuBySku_id(sku.getSku_id());
        }

        skuCache.removeSkuListByProduct_id(product_id);

        JMap map = JMap.create();
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

}