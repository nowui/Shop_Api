package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.service.MemberService;

import java.util.List;

public class MemberController extends Controller {

    private static final MemberService memberService = new MemberService();

    @ActionKey(Url.MEMBER_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_NAME);

        List<Member> memberList = memberService.list(model, getM(), getN());

        renderSuccessJson(memberList);
    }

    @ActionKey(Url.MEMBER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_NAME);

        int count = memberService.count(model);

        List<Member> memberList = memberService.list(model, getM(), getN());

        renderSuccessJson(count, memberList);
    }

    @ActionKey(Url.MEMBER_FIND)
    public void find() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = memberService.find(model.getMember_id());

        member.removeUnfindable();

        renderSuccessJson(member);
    }

    @ActionKey(Url.MEMBER_ADMIN_FIND)
    public void adminFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = memberService.find(model.getMember_id());

        renderSuccessJson(member);
    }

    @ActionKey(Url.MEMBER_SAVE)
    public void save() {
        Member model = getParameter(Member.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_NAME);

        memberService.save(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBERL_UPDATE)
    public void update() {
        Member model = getParameter(Member.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_ID, Member.MEMBER_NAME);

        memberService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_DELETE)
    public void delete() {
        Member model = getParameter(Member.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_ID);

        memberService.delete(model, request_user_id);

        renderSuccessJson();
    }

}