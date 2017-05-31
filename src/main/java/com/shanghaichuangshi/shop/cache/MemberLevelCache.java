package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.MemberLevelDao;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class MemberLevelCache extends Cache {

    private final String MEMBER_LEVEL_LIST_CACHE = "member_level_list_cache";
    private final String MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE = "member_level_by_member_level_id_cache";

    private MemberLevelDao memberLevelDao = new MemberLevelDao();

    public int count(String member_level_name) {
        return memberLevelDao.count(member_level_name);
    }

    public List<MemberLevel> list(String member_level_name, Integer m, Integer n) {
        return memberLevelDao.list(member_level_name, m, n);
    }

    public List<MemberLevel> listAll() {
        List<MemberLevel> memberLevelList = CacheUtil.get(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);

        if (memberLevelList == null) {
            memberLevelList = memberLevelDao.listAll();

            CacheUtil.put(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE, memberLevelList);
        }

        return memberLevelList;
    }

    public MemberLevel find(String member_level_id) {
        MemberLevel memberLevel = CacheUtil.get(MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE, member_level_id);

        if (memberLevel == null) {
            memberLevel = memberLevelDao.find(member_level_id);

            CacheUtil.put(MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE, member_level_id, memberLevel);
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
            memberLevel = memberLevelDao.findByMember_level_value(member_level_value);

            CacheUtil.put(MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE, memberLevel.getMember_level_id(), memberLevel);
        }

        return memberLevel;
    }

    public MemberLevel save(MemberLevel member_level, String request_user_id) {
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);

        return memberLevelDao.save(member_level, request_user_id);
    }

    public boolean update(MemberLevel member_level, String request_user_id) {
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);
        CacheUtil.remove(MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE, member_level.getMember_level_id());

        return memberLevelDao.update(member_level, request_user_id);
    }

    public boolean delete(String member_level_id, String request_user_id) {
        CacheUtil.remove(MEMBER_LEVEL_LIST_CACHE, MEMBER_LEVEL_LIST_CACHE);
        CacheUtil.remove(MEMBER_LEVEL_BY_MEMBER_LEVEL_ID_CACHE, member_level_id);

        return memberLevelDao.delete(member_level_id, request_user_id);
    }

}