package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommissionDao extends Dao {

    private final String COMMISSION_LIST_CACHE = "commission_list_cache";
    private final String COMMISSION_CACHE = "commission_cache";

    public List<Commission> list(String product_id) {
        List<Commission> commissionList = CacheUtil.get(COMMISSION_LIST_CACHE, product_id);

        if (commissionList == null) {
            JMap map = JMap.create();
            map.put(Commission.PRODUCT_ID, product_id);
            SqlPara sqlPara = Db.getSqlPara("commission.list", map);

            commissionList = new Commission().find(sqlPara.getSql(), sqlPara.getPara());

            if (commissionList != null) {
                CacheUtil.put(COMMISSION_LIST_CACHE, product_id, commissionList);
            }
        }

        return commissionList;
    }

    public Commission find(String commission_id) {
        Commission commission = CacheUtil.get(COMMISSION_CACHE, commission_id);

        if (commission == null) {
            JMap map = JMap.create();
            map.put(Commission.COMMISSION_ID, commission_id);
            SqlPara sqlPara = Db.getSqlPara("commission.find", map);

            List<Commission> commissionList = new Commission().find(sqlPara.getSql(), sqlPara.getPara());
            if (commissionList.size() == 0) {
                commission = null;
            } else {
                commission = commissionList.get(0);

                CacheUtil.put(COMMISSION_CACHE, commission_id, commission);
            }
        }

        return commission;
    }

    public void save(List<Commission> commissionList, String request_user_id) {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("commission.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Commission commission : commissionList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(Util.getRandomUUID());
            objectList.add(commission.getProduct_id());
            objectList.add(commission.getProduct_attribute());
            objectList.add(commission.getProduct_commission());
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
                throw new RuntimeException("佣金保存不成功");
            }
        }
    }

    public void delete(List<Commission> commissionList, String product_id, String request_user_id) {
        for(Commission commission : commissionList) {
            CacheUtil.remove(COMMISSION_CACHE, commission.getCommission_id());
        }

        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("commission.delete", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Commission commission : commissionList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(commission.getCommission_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("佣金删除不成功");
            }
        }
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        List<String> keyList = CacheUtil.getKeys(COMMISSION_CACHE);

        for(String key : keyList) {
            Commission commission = CacheUtil.get(COMMISSION_CACHE, key);

            if (commission.getProduct_id().equals(product_id)) {
                CacheUtil.remove(COMMISSION_CACHE, product_id);
            }
        }

        CacheUtil.remove(COMMISSION_LIST_CACHE, product_id);

        JMap map = JMap.create();
        map.put(Commission.PRODUCT_ID, product_id);
        map.put(Commission.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Commission.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("scene.deleteByProduct_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}