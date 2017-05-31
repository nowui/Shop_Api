package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.MemberDao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.List;

public class MemberCache extends Cache {

    private final String MEMBER_LIST_BY_PARENT_ID_CACHE = "membe_list_by_parent_id_cache";
    private final String MEMBER_BY_MEMBER_ID_CACHE = "membe_by_member_id_cache";

    private MemberDao memberDao = new MemberDao();

    public int count(String member_name) {
        return memberDao.count(member_name);
    }

    public List<Member> list(String member_name, Integer m, Integer n) {
        return memberDao.list(member_name, m, n);
    }

    public List<Member> treeList() {
        return memberDao.treeList();
    }

    public List<Member> teamList(String parent_id) {
        List<Member> memberList = CacheUtil.get(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id);

        if (memberList == null) {
            memberList = memberDao.teamList(parent_id);

            CacheUtil.put(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id, memberList);
        }

        return memberList;
    }

    public Member find(String member_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        if (member == null) {
            member = memberDao.find(member_id);

            CacheUtil.put(MEMBER_BY_MEMBER_ID_CACHE, member_id, member);
        }

        return member;
    }

    public Member save(String parent_id, String parent_path, String user_id, String from_scene_id, String scene_id, String scene_qrcode, BigDecimal member_total_amount, BigDecimal member_withdrawal_amount, BigDecimal member_month_order_amount, BigDecimal member_all_order_amount, String member_level_id, String user_name, String member_phone, String member_remark, Boolean member_status, String request_user_id) {
        if (Util.isNullOrEmpty(parent_id)) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, parent_id);
        }

        return memberDao.save(parent_id, parent_path, user_id, from_scene_id, scene_id, scene_qrcode, member_total_amount, member_withdrawal_amount, member_month_order_amount, member_all_order_amount, member_level_id, user_name, member_phone, member_remark, member_status, request_user_id);
    }

    public boolean childrenUpdate(String member_id, String member_level_id, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        return memberDao.childrenUpdate(member_id, member_level_id, request_user_id);
    }

    public boolean updateByMember_idAndScene_idAndScene_qrcode(String member_id, String scene_id, String scene_qrcode, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        return updateByMember_idAndScene_idAndScene_qrcode(member_id, scene_id, scene_qrcode, request_user_id);
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);


        return memberDao.updateByMember_idAndParent_idAndParent_pathAndMember_level_id(member_id, parent_id, parent_path, member_level_id);
    }

    public void updateAmount(List<Member> memberList) {
        for(Member member : memberList) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());

            CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member.getMember_id());
        }

        memberDao.updateAmount(memberList);
    }

    public boolean updateByMember_idAndMember_name(String member_id, String member_name, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        return memberDao.updateByMember_idAndMember_name(member_id, member_name, request_user_id);
    }

    public boolean updateByMember_idAndMember_level_id(String member_id, String member_level_id, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        return memberDao.updateByMember_idAndMember_level_id(member_id, member_level_id, request_user_id);
    }

    public boolean delete(String member_id, String request_user_id) {
        Member member = CacheUtil.get(MEMBER_BY_MEMBER_ID_CACHE, member_id);
        if (member != null) {
            CacheUtil.remove(MEMBER_LIST_BY_PARENT_ID_CACHE, member.getParent_id());
        }

        CacheUtil.remove(MEMBER_BY_MEMBER_ID_CACHE, member_id);

        return memberDao.delete(member_id, request_user_id);
    }

}