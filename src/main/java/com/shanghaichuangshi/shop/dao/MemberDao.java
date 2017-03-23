package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.cache.MemberCache;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class MemberDao extends Dao {

    private final MemberCache memberCache = new MemberCache();

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

    public Member find(String member_id) {
        Member member = memberCache.getMemberByMember_id(member_id);

        if (member == null) {
            JMap map = JMap.create();
            map.put(Member.MEMBER_ID, member_id);
            SqlPara sqlPara = Db.getSqlPara("member.find", map);

            List<Member> memberList = new Member().find(sqlPara.getSql(), sqlPara.getPara());
            if (memberList.size() == 0) {
                member = null;
            } else {
                member = memberList.get(0);

                memberCache.setMemberByMember_id(member, member_id);
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
        memberCache.removeMemberByMember_id(member.getMember_id());

        member.remove(Member.SYSTEM_CREATE_USER_ID);
        member.remove(Member.SYSTEM_CREATE_TIME);
        member.setSystem_update_user_id(request_user_id);
        member.setSystem_update_time(new Date());
        member.remove(Member.SYSTEM_STATUS);

        return member.update();
    }

    public boolean updateByMember_idAndUser_id(String member_id, String user_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.USER_ID, user_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.updateByMember_idAndUser_id", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String member_id, String request_user_id) {
        memberCache.removeMemberByMember_id(member_id);

        JMap map = JMap.create();
        map.put(Member.MEMBER_ID, member_id);
        map.put(Member.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Member.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("member.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}