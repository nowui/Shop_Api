package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.MemberLevelDao;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class MemberLevelService extends Service {

    private final MemberLevelDao memberLevelDao = new MemberLevelDao();

    public int count(MemberLevel member_level) {
        return memberLevelDao.count(member_level.getMember_level_name());
    }

    public List<MemberLevel> list(MemberLevel member_level, int m, int n) {
        return memberLevelDao.list(member_level.getMember_level_name(), m, n);
    }

    public List<MemberLevel> listAll() {
        return memberLevelDao.listAll();
    }

    public MemberLevel find(String member_level_id) {
        return memberLevelDao.find(member_level_id);
    }

    public MemberLevel findTopMember_level() {
        return memberLevelDao.findByMember_level_value(1);
    }

    public MemberLevel findNextMember_levelByMember_level_id(String member_level_id) {
        MemberLevel memberLevel = memberLevelDao.find(member_level_id);
        return memberLevelDao.findByMember_level_value(memberLevel.getMember_level_value() + 1);
    }

    public MemberLevel save(MemberLevel member_level, String request_user_id) {
        return memberLevelDao.save(member_level, request_user_id);
    }

    public boolean update(MemberLevel member_level, String request_user_id) {
        return memberLevelDao.update(member_level, request_user_id);
    }

    public boolean delete(MemberLevel member_level, String request_user_id) {
        return memberLevelDao.delete(member_level.getMember_level_id(), request_user_id);
    }

}