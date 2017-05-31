package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.MemberLevelCache;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class MemberLevelService extends Service {

    private final MemberLevelCache memberLevelCache = new MemberLevelCache();

    public int count(String member_level_name) {
        return memberLevelCache.count(member_level_name);
    }

    public List<MemberLevel> list(String member_level_name, int m, int n) {
        return memberLevelCache.list(member_level_name, m, n);
    }

    public List<MemberLevel> listAll() {
        return memberLevelCache.listAll();
    }

    public MemberLevel find(String member_level_id) {
        return memberLevelCache.find(member_level_id);
    }

    public MemberLevel findTopMember_level() {
        return memberLevelCache.findByMember_level_value(1);
    }

    public MemberLevel findNextMember_levelByMember_level_id(String member_level_id) {
        MemberLevel memberLevel = memberLevelCache.find(member_level_id);
        return memberLevelCache.findByMember_level_value(memberLevel.getMember_level_value() + 1);
    }

    public MemberLevel save(MemberLevel member_level, String request_user_id) {
        return memberLevelCache.save(member_level, request_user_id);
    }

    public boolean update(MemberLevel member_level, String request_user_id) {
        return memberLevelCache.update(member_level, request_user_id);
    }

    public boolean delete(MemberLevel member_level, String request_user_id) {
        return memberLevelCache.delete(member_level.getMember_level_id(), request_user_id);
    }

}