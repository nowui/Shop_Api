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

    private final String MEMBER_LEVEL_LIST_CACHE = "member_level_list_cache";
    private final String MEMBER_LEVEL_CACHE = "member_level_cache";

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
        List<MemberLevel> memberLevelList = CacheUtil.get(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);

        if (memberLevelList == null) {
            Kv map = Kv.create();
            map.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            map.put(MemberLevel.M, 0);
            map.put(MemberLevel.N, 0);
            SqlPara sqlPara = Db.getSqlPara("member_level.list", map);

            memberLevelList = new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());

            if (memberLevelList.size() > 0) {
                CacheUtil.put(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE, memberLevelList);
            }
        }

        return memberLevelList;
    }

    public MemberLevel find(String member_level_id) {
        MemberLevel memberLevel = CacheUtil.get(MEMBER_LEVEL_CACHE, member_level_id);

        if (memberLevel == null) {
            Kv map = Kv.create();
            map.put(MemberLevel.MEMBER_LEVEL_ID, member_level_id);
            SqlPara sqlPara = Db.getSqlPara("member_level.find", map);

            List<MemberLevel> member_levelList = new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
            if (member_levelList.size() == 0) {
                memberLevel = null;
            } else {
                memberLevel = member_levelList.get(0);

                CacheUtil.put(MEMBER_LEVEL_CACHE, member_level_id, memberLevel);
            }
        }

        return memberLevel;
    }

    public MemberLevel findByMember_level_value(Integer member_level_value) {
        MemberLevel memberLevel = null;

        List<MemberLevel> memberLevelList = CacheUtil.get(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);

        if (memberLevelList != null) {
            for (MemberLevel m : memberLevelList) {
                if (m.getMember_level_value().equals(member_level_value)) {
                    memberLevel = m;

                    break;
                }
            }
        }

        if (memberLevel == null) {
            Kv map = Kv.create();
            map.put(MemberLevel.MEMBER_LEVEL_VALUE, member_level_value);
            SqlPara sqlPara = Db.getSqlPara("member_level.findByMember_level_value", map);

            List<MemberLevel> member_levelList = new MemberLevel().find(sqlPara.getSql(), sqlPara.getPara());
            if (member_levelList.size() == 0) {
                memberLevel = null;
            } else {
                memberLevel = member_levelList.get(0);

                CacheUtil.put(MEMBER_LEVEL_CACHE, memberLevel.getMember_level_id(), memberLevel);
            }
        }

        return memberLevel;
    }

    public MemberLevel save(MemberLevel member_level, String request_user_id) {
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);

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
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);
        CacheUtil.remove(MEMBER_LEVEL_CACHE, member_level.getMember_level_id());

        member_level.remove(MemberLevel.SYSTEM_CREATE_USER_ID);
        member_level.remove(MemberLevel.SYSTEM_CREATE_TIME);
        member_level.setSystem_update_user_id(request_user_id);
        member_level.setSystem_update_time(new Date());
        member_level.remove(MemberLevel.SYSTEM_STATUS);

        return member_level.update();
    }

    public boolean delete(String member_level_id, String request_user_id) {
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);
        CacheUtil.remove(MEMBER_LEVEL_CACHE, member_level_id);

        Kv map = Kv.create();
        map.put(MemberLevel.MEMBER_LEVEL_ID, member_level_id);
        map.put(MemberLevel.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(MemberLevel.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member_level.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}