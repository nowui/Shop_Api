package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class MemberLevelDao extends Dao {

    public int count(String member_level_name) {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_NAME, member_level_name);
        SqlPara sqlPara = Db.getSqlPara("member_level.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<MemberLevel> list(String member_level_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_NAME, member_level_name);
        map.put(MemberLevel.M, m);
        map.put(MemberLevel.N, n);
        SqlPara sqlPara = Db.getSqlPara("member_level.list", map);

        return new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<MemberLevel> listAll() {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_NAME, "");
        map.put(MemberLevel.M, 0);
        map.put(MemberLevel.N, 0);
        SqlPara sqlPara = Db.getSqlPara("member_level.list", map);

        return new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public MemberLevel find(String member_level_id) {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_ID, member_level_id);
        SqlPara sqlPara = Db.getSqlPara("member_level.find", map);

        List<MemberLevel> member_levelList = new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
        if (member_levelList.size() == 0) {
            return null;
        } else {
            return member_levelList.get(0);
        }
    }

    public MemberLevel findByMember_level_value(Integer member_level_value) {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_VALUE, member_level_value);
        SqlPara sqlPara = Db.getSqlPara("member_level.findByMember_level_value", map);

        List<MemberLevel> member_levelList = new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
        if (member_levelList.size() == 0) {
            return null;
        } else {
            return member_levelList.get(0);
        }
    }

    public MemberLevel save(MemberLevel member_level, String request_user_id) {
        member_level.setMember_level_id(Util.getRandomUUID());
        member_level.setSystem_create_user_id(request_user_id);
        member_level.setSystem_create_time(new Date());
        member_level.setSystem_update_user_id(request_user_id);
        member_level.setSystem_update_time(new Date());
        member_level.setSystem_status(true);

        member_level.save();

        return member_level;
    }

    public boolean update(MemberLevel member_level, String request_user_id) {
        member_level.remove(MemberLevel.SYSTEM_CREATE_USER_ID);
        member_level.remove(MemberLevel.SYSTEM_CREATE_TIME);
        member_level.setSystem_update_user_id(request_user_id);
        member_level.setSystem_update_time(new Date());
        member_level.remove(MemberLevel.SYSTEM_STATUS);

        return member_level.update();
    }

    public boolean delete(String member_level_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_ID, member_level_id);
        map.put(MemberLevel.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(MemberLevel.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member_level.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}