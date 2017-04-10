package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.service.MemberLevelService;
import com.shanghaichuangshi.shop.service.MemberService;

import java.util.List;
import java.util.Map;

public class MemberController extends Controller {

    private final MemberService memberService = new MemberService();
    private final MemberLevelService memberLevelService = new MemberLevelService();

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

        renderSuccessJson(member.removeUnfindable());
    }

    @ActionKey(Url.MEMBER_ADMIN_FIND)
    public void adminFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = memberService.find(model.getMember_id());

        renderSuccessJson(member);
    }

    @ActionKey(Url.MEMBER_QRCODE_FIND)
    public void qrcodeFind() {
        String request_user_id = getRequest_user_id();

        String scene_qrcode = memberService.qrcodeFind(request_user_id);

        renderSuccessJson(scene_qrcode);
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
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_ID, Member.MEMBER_NAME);

        memberService.update(model, userModel, request_user_id);

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

    @ActionKey(Url.MEMBER_LOGIN)
    public void login() {
        User model = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        Map<String, Object> resultMap = memberService.login(model, getPlatform(), getVersion(), getIp_address(), request_user_id);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.MEMBER_WECHAT_LOGIN)
    public void weChatLogin() {
        User model = getParameter(User.class);

        Map<String, Object> resultMap = memberService.weChatLogin(model.getWechat_open_id());

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_LIST)
    public void levelAdminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        MemberLevel model = getParameter(MemberLevel.class);

        model.validate(MemberLevel.MEMBER_LEVEL_NAME);

        int count = memberLevelService.count(model);

        List<MemberLevel> memberLevelListvice = memberLevelService.list(model, getM(), getN());

        renderSuccessJson(count, memberLevelListvice);
    }

    @ActionKey(Url.MEMBER_LEVEL_CATEGORY_LIST)
    public void levelCategoryList() {

        List<MemberLevel> memberLevelListvice = memberLevelService.list(new MemberLevel(), 0, 0);

        renderSuccessJson(memberLevelListvice);
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_FIND)
    public void levelAdminFind() {
        MemberLevel model = getParameter(MemberLevel.class);

        model.validate(MemberLevel.MEMBER_LEVEL_ID);

        MemberLevel member_level = memberLevelService.find(model.getMember_level_id());

        renderSuccessJson(member_level);
    }

    @ActionKey(Url.MEMBER_LEVEL_SAVE)
    public void levelSave() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_NAME);

        memberLevelService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_LEVELL_UPDATE)
    public void levelUpdate() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_ID, MemberLevel.MEMBER_LEVEL_NAME);

        memberLevelService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_LEVEL_DELETE)
    public void levelDelete() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_ID);

        memberLevelService.delete(model, request_user_id);

        renderSuccessJson();
    }

}