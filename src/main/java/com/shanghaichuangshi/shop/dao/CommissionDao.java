package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommissionDao extends Dao {

    public List<Commission> list(String product_id) {
        Kv map = Kv.create();
        map.put(Commission.PRODUCT_ID, product_id);
        SqlPara sqlPara = Db.getSqlPara("commission.list", map);

        return new Commission().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Commission find(String commission_id) {
        Kv map = Kv.create();
        map.put(Commission.COMMISSION_ID, commission_id);
        SqlPara sqlPara = Db.getSqlPara("commission.find", map);

        List<Commission> commissionList = new Commission().find(sqlPara.getSql(), sqlPara.getPara());
        if (commissionList.size() == 0) {
            return null;
        } else {
            return commissionList.get(0);
        }
    }

    public void save(List<Commission> commissionList, String request_user_id) {
        if (commissionList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
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
        if (commissionList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
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
        Kv map = Kv.create();
        map.put(Commission.PRODUCT_ID, product_id);
        map.put(Commission.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Commission.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("commission.deleteByProduct_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}