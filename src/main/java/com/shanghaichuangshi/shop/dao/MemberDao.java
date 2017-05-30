package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDao extends Dao {

    private final String MEMBER_LIST_BY_PARENT_ID_CACHE = "membe_list_by_parent_id_cache";
    private final String MEMBER_BY_MEMBER_ID_CACHE = "membe_by_member_id_cache";

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
        List<Member> memberList = CacheUtil.get(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id);

        if (memberList == null) {
            Kv map = Kv.create();
            map.put(Member.PARENT_ID, parent_id);
            SqlPara sqlPara = Db.getSqlPara("member.teamList", map);

            memberList = new Member().find(sqlPara.getSql(), sqlPara.getPara());

            if (memberList.size() > 0) {
                CacheUtil.put(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id, memberList);
            }
        }

        return memberList;
    }

    public Member find(String member_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        if (member == null) {
            Kv map = Kv.create();
            map.put(Member.MEMBER_ID, member_id);
            SqlPara sqlPara = Db.getSqlPara("member.find", map);

            List<Member> memberList = new Member().find(sqlPara.getSql(), sqlPara.getPara());
            if (memberList.size() == 0) {
                member = null;
            } else {
                member = memberList.get(0);

                CacheUtil.put(MEMBER_BY_MEMBER_ID_CACHE, member_id, member);
            }
        }

        return member;
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
        member.setMember_total_amount(member_total_amount);
        member.setMember_withdrawal_amount(member_withdrawal_amount);
        member.setMember_month_order_amount(member_month_order_amount);
        member.setMember_all_order_amount(member_all_order_amount);
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

        if (Util.isNullOrEmpty(parent_id)) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id);
        }

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
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.childrenUpdate", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndScene_idAndScene_qrcode(String member_id, String scene_id, String scene_qrcode, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

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
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.PARENT_ID, parent_id);
        map.put(Member.PARENT_PATH, parent_path);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndParent_idAndParent_pathAndMember_level_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public void updateAmount(List<Member> memberList) {
        if (memberList.size() == 0) {
            return;
        }

        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("member.updateAmount", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(Member member : memberList) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());

            CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member.getMember_id());

            List<Object> objectList = new ArrayList<Object>();
            objectList.add(member.getMember_total_amount());
            objectList.add(member.getMember_withdrawal_amount());
            objectList.add(member.getMember_month_order_amount());
            objectList.add(member.getMember_all_order_amount());
            objectList.add(member.getMember_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("金额更新不成功");
            }
        }
    }

    public boolean updateByMember_idAndMember_name(String member_id, String member_name, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_NAME, member_name);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndMember_name", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean updateByMember_idAndMember_level_id(String member_id, String member_level_id, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.MEMBER_LEVEL_ID, member_level_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndMember_level_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String member_id, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}