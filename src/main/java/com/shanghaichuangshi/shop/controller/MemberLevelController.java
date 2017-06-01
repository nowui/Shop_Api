package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.service.MemberLevelService;

import java.util.List;

public class MemberLevelController extends Controller {

    private final MemberLevelService memberLevelService = new MemberLevelService();

    @ActionKey(Url.MEMBER_LEVEL_LIST)
    public void list() {

        List<MemberLevel> memberLevelListvice = memberLevelService.list("", 0, 0);

        renderSuccessJson(memberLevelListvice);
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        MemberLevel model = getParameter(MemberLevel.class);

        model.validate(MemberLevel.MEMBER_LEVEL_NAME);

        int count = memberLevelService.count(model.getMember_level_name());

        List<MemberLevel> memberLevelListvice = memberLevelService.list(model.getMember_level_name(), getM(), getN());

        renderSuccessJson(count, memberLevelListvice);
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_FIND)
    public void levelAdminFind() {
        MemberLevel model = getParameter(MemberLevel.class);

        model.validate(MemberLevel.MEMBER_LEVEL_ID);

        MemberLevel member_level = memberLevelService.find(model.getMember_level_id());

        renderSuccessJson(member_level);
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_SAVE)
    public void adminSave() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_NAME);

        memberLevelService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_LEVELL_ADMIN_UPDATE)
    public void adminUpdate() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_ID, MemberLevel.MEMBER_LEVEL_NAME);

        memberLevelService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_LEVEL_ADMIN_DELETE)
    public void adminDelete() {
        MemberLevel model = getParameter(MemberLevel.class);
        String request_user_id = getRequest_user_id();

        model.validate(MemberLevel.MEMBER_LEVEL_ID);

        memberLevelService.delete(model, request_user_id);

        renderSuccessJson();
    }

}