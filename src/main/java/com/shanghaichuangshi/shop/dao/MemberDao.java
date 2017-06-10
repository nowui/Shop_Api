package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDao extends Dao {

    public int count(String member_name) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_NAME, member_name);
        SqlPara sqlPara = Db.getSqlPara("member.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Member> list(String member_name, Integer m, Integer n) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_NAME, member_name);
        map.put(Member.M, m);
        map.put(Member.N, n);
        SqlPara sqlPara = Db.getSqlPara("member.list", map);

        return new Member().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Member> treeList() {
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("member.treeList", map);

        return new Member().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Member> teamList(String parent_id) {
        Kv map = Kv.create();
        map.put(Member.PARENT_ID, parent_id);
        SqlPara sqlPara = Db.getSqlPara("member.teamList", map);

        return new Member().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Member find(String member_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        SqlPara sqlPara = Db.getSqlPara("member.find", map);

        List<Member> memberList = new Member().find(sqlPara.getSql(), sqlPara.getPara());
        if (memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }
    }

    public Member save(String parent_id, String parent_path, String user_id, String from_scene_id, String scene_id, String scene_qrcode, BigDecimal member_total_amount, BigDecimal member_withdrawal_amount, BigDecimal member_month_order_amount, BigDecimal member_all_order_amount, String member_level_id, String user_name, String member_phone, String member_remark, Boolean member_status, String request_user_id) {
        Member member = new Member();
        member.setParent_id(parent_id);
        member.setParent_path(parent_path);
        member.setParent_id(parent_id);
        member.setUser_id(user_id);
        member.setFrom_scene_id(from_scene_id);
        member.setScene_id(scene_id);
        member.setScene_qrcode(scene_qrcode);
        member.setMember_level_id(member_level_id);
        member.setMember_name(user_name);
        member.setMember_phone(member_phone);
        member.setMember_remark(member_remark);
        member.setMember_status(member_status);
        member.setMember_id(Util.getRandomUUID());
        member.setSystem_create_user_id(request_user_id);
        member.setSystem_create_time(new Date());
        member.setSystem_update_user_id(request_user_id);
        member.setSystem_update_time(new Date());
        member.setSystem_status(true);

        member.save();

        return member;
    }

//    public boolean update(Member member, String request_user_id) {
//        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member.getMember_id());
//
//        member.remove(Member.MEMBER_TOTAL_AMOUNT);
//        member.remove(Member.MEMBER_WITHDRAWAL_AMOUNT);
//        member.remove(Member.SYSTEM_CREATE_USER_ID);
//        member.remove(Member.SYSTEM_CREATE_TIME);
//        member.setSystem_update_user_id(request_user_id);
//        member.setSystem_update_time(new Date());
//        member.remove(Member.SYSTEM_STATUS);
//
//        return member.update();
//    }

    public boolean childrenUpdate(String member_id, String member_level_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.childrenUpdate", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndScene_idAndScene_qrcode(String member_id, String scene_id, String scene_qrcode, String request_user_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SCENE_ID, scene_id);
        map.put(Member.SCENE_QRCODE, scene_qrcode);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndScene_idAndScene_qrcode", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.PARENT_ID, parent_id);
        map.put(Member.PARENT_PATH, parent_path);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndParent_idAndParent_pathAndMember_level_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndMember_name(String member_id, String member_name, String request_user_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_NAME, member_name);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndMember_name", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndMember_level_id(String member_id, String member_level_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndMember_level_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String member_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}