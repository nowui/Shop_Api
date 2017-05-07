package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Distributor;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.Date;
import java.util.List;

public class DistributorDao extends Dao {

    private final String DISTRIBUTOR_CACHE = "distributor_cache";

    public int count(String distributor_name) {
        Kv map = Kv.create();
        map.put(Distributor.DISTRIBUTOR_NAME, distributor_name);
        SqlPara sqlPara = Db.getSqlPara("distributor.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Distributor> list(String distributor_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Distributor.DISTRIBUTOR_NAME, distributor_name);
        map.put(Distributor.M, m);
        map.put(Distributor.N, n);
        SqlPara sqlPara = Db.getSqlPara("distributor.list", map);

        return new Distributor().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Distributor find(String distributor_id) {
        Distributor distributor = CacheUtil.get(DISTRIBUTOR_CACHE, distributor_id);

        if (distributor == null) {
            Kv map = Kv.create();
            map.put(Distributor.DISTRIBUTOR_ID, distributor_id);
            SqlPara sqlPara = Db.getSqlPara("distributor.find", map);

            List<Distributor> distributorList = new Distributor().find(sqlPara.getSql(), sqlPara.getPara());
            if (distributorList.size() == 0) {
                distributor = null;
            } else {
                distributor = distributorList.get(0);

                CacheUtil.put(DISTRIBUTOR_CACHE, distributor_id, distributor);
            }
        }

        return distributor;
    }

    public Distributor save(String distributor_id, Distributor distributor, String request_user_id) {
        distributor.setDistributor_id(distributor_id);
        distributor.setSystem_create_user_id(request_user_id);
        distributor.setSystem_create_time(new Date());
        distributor.setSystem_update_user_id(request_user_id);
        distributor.setSystem_update_time(new Date());
        distributor.setSystem_status(true);

        distributor.save();

        return distributor;
    }

    public boolean update(Distributor distributor, String request_user_id) {
        CacheUtil.remove(DISTRIBUTOR_CACHE, distributor.getDistributor_id());

        distributor.remove(Distributor.SYSTEM_CREATE_USER_ID);
        distributor.remove(Distributor.SYSTEM_CREATE_TIME);
        distributor.setSystem_update_user_id(request_user_id);
        distributor.setSystem_update_time(new Date());
        distributor.remove(Distributor.SYSTEM_STATUS);

        return distributor.update();
    }

    public boolean delete(String distributor_id, String request_user_id) {
        CacheUtil.remove(DISTRIBUTOR_CACHE, distributor_id);

        Kv map = Kv.create();
        map.put(Distributor.DISTRIBUTOR_ID, distributor_id);
        map.put(Distributor.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Distributor.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("distributor.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}