package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.dao.MemberDao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.UserType;

import java.util.List;

public class MemberService extends Service {

    private static final MemberDao memberDao = new MemberDao();

    private static final UserService userService = new UserService();

    public int count(Member member) {
        return memberDao.count(member.getMember_name());
    }

    public List<Member> list(Member member, int m, int n) {
        return memberDao.list(member.getMember_name(), m, n);
    }

    public Member find(String member_id) {
        return memberDao.find(member_id);
    }

    public Member save(Member member, User user, String request_user_id) {
        Member m = memberDao.save(member, request_user_id);

        String user_id = userService.saveByUser_phoneAndUser_passwordAndObject_idAndUser_type(user.getUser_phone(), user.getUser_password(), m.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

        memberDao.updateByMember_idAndUser_id(m.getMember_id(), user_id, request_user_id);

        m.setUser_id(user_id);

        return m;
    }

    public boolean update(Member member, String request_user_id) {
        return memberDao.update(member, request_user_id);
    }

    public boolean delete(Member member, String request_user_id) {
        return memberDao.delete(member.getMember_id(), request_user_id);
    }

}