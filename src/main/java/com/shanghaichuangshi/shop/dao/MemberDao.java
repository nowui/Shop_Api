package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class MemberDao extends Dao {

    private final String MEMBER_CACHE = "membe_cache";

    public int count(String member_name) {
        JMap map = JMap.create();
        map.put(Member.MEMBER_NAME, member_name);
        SqlPara sqlPara = Db.getSqlPara("member.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Member> list(String member_name, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Member.MEMBER_NAME, member_name);
        map.put(Member.M, m);
        map.put(Member.N, n);
        SqlPara sqlPara = Db.getSqlPara("member.list", map);

        return new Member().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Member> teamList(String parent_id, Integer m, Integer n) {
        JMap map = JMap.create();
        map.put(Member.PARENT_ID, parent_id);
        map.put(Member.M, m);
        map.put(Member.N, n);
        SqlPara sqlPara = Db.getSqlPara("member.teamList", map);

        return new Member().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Member find(String member_id) {
        Member member = CacheUtil.get(MEMBER_CACHE, member_id);

        if (member == null) {
            JMap map = JMap.create();
            map.put(Member.MEMBER_ID, member_id);
            SqlPara sqlPara = Db.getSqlPara("member.find", map);

            List<Member> memberList = new Member().find(sqlPara.getSql(), sqlPara.getPara());
            if (memberList.size() == 0) {
                member = null;
            } else {
                member = memberList.get(0);

                CacheUtil.put(MEMBER_CACHE, member_id, member);
            }
        }

        return member;
    }

    public Member save(Member member, String request_user_id) {
        member.setMember_id(Util.getRandomUUID());
        member.setSystem_create_user_id(request_user_id);
        member.setSystem_create_time(new Date());
        member.setSystem_update_user_id(request_user_id);
        member.setSystem_update_time(new Date());
        member.setSystem_status(true);

        member.save();

        return member;
    }

    public boolean update(Member member, String request_user_id) {
        CacheUtil.remove(MEMBER_CACHE, member.getMember_id());

        member.remove(Member.SYSTEM_CREATE_USER_ID);
        member.remove(Member.SYSTEM_CREATE_TIME);
        member.setSystem_update_user_id(request_user_id);
        member.setSystem_update_time(new Date());
        member.remove(Member.SYSTEM_STATUS);

        return member.update();
    }

    public boolean updateByMember_idAndScene_idAndScene_qrcode(String member_id, String scene_id, String scene_qrcode, String request_user_id) {
        CacheUtil.remove(MEMBER_CACHE, member_id);

        JMap map = JMap.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SCENE_ID, scene_id);
        map.put(Member.SCENE_QRCODE, scene_qrcode);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndScene_idAndScene_qrcode", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        CacheUtil.remove(MEMBER_CACHE, member_id);

        JMap map = JMap.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.PARENT_ID, parent_id);
        map.put(Member.PARENT_PATH, parent_path);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndParent_idAndParent_pathAndMember_level_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String member_id, String request_user_id) {
        CacheUtil.remove(MEMBER_CACHE, member_id);

        JMap map = JMap.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}