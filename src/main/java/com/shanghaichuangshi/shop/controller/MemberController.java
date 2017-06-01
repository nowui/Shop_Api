package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.service.MemberLevelService;
import com.shanghaichuangshi.shop.service.MemberService;
import com.shanghaichuangshi.shop.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberController extends Controller {

    private final UserService userService = new UserService();
    private final MemberService memberService = new MemberService();
    private final MemberLevelService memberLevelService = new MemberLevelService();
    private final OrderService orderService = new OrderService();

    @ActionKey(Url.MEMBER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_NAME);

        int count = memberService.count(model.getMember_name());

        List<Member> memberList = memberService.list(model.getMember_name(), getM(), getN());

        renderSuccessJson(count, memberList);
    }

    @ActionKey(Url.MEMBER_ADMIN_TREE_LIST)
    public void adminTreeList() {
        List<Member> memberList = memberService.adminTreeList();

        renderSuccessJson( memberList);
    }

    @ActionKey(Url.MEMBER_TEAM_LIST)
    public void teamList() {
        String request_user_id = getRequest_user_id();

        List<Member> memberList = memberService.teamList(request_user_id);

        renderSuccessJson(memberList);
    }

    @ActionKey(Url.MEMBER_ADMIN_FIND)
    public void adminFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = memberService.find(model.getMember_id());

        renderSuccessJson(member.removeSystemInfo());
    }

    @ActionKey(Url.MEMBER_QRCODE_FIND)
    public void qrcodeFind() {
        String request_user_id = getRequest_user_id();

        String scene_qrcode = memberService.qrcodeFind(request_user_id);

        renderSuccessJson(scene_qrcode);
    }

    @ActionKey(Url.MEMBER_MY_FIND)
    public void myFind() {
        String request_user_id = getRequest_user_id();

        Map<String, Object> resultMap = memberService.myFind(request_user_id);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.MEMBER_TEAM_FIND)
    public void teamFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = memberService.teamFind(model.getMember_id());

        User user = userService.find(member.getUser_id());

        member.put(User.USER_AVATAR, user.getUser_avatar());

        if (member.getMember_status()) {
            MemberLevel memberLevel = memberLevelService.find(member.getMember_level_id());
            member.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());

            List<Order> orderList = orderService.listByUser_id(member.getUser_id());
            member.put(Order.ORDER_LIST, orderList);

            BigDecimal member_month_order_amount = BigDecimal.ZERO;
            BigDecimal member_all_order_amount = BigDecimal.ZERO;
            for (Order order : orderList) {
                if (order.getOrder_status() && order.getOrder_is_pay()) {
                    member_month_order_amount = member_month_order_amount.add(order.getOrder_amount());
                    member_all_order_amount = member_all_order_amount.add(order.getOrder_amount());
                }
            }
            member.put(Member.MEMBER_MONTH_ORDER_AMOUNT, member_month_order_amount);
            member.put(Member.MEMBER_ALL_ORDER_AMOUNT, member_all_order_amount);
        } else {
            member.put(MemberLevel.MEMBER_LEVEL_NAME, "");

            if (!member.getMember_status()) {
                Member parentMember = memberService.find(member.getParent_id());
                MemberLevel parentMemberLevel = memberLevelService.find(parentMember.getMember_level_id());
                List<MemberLevel> list = new ArrayList<MemberLevel>();
                List<MemberLevel> memberLevelList = memberLevelService.listAll();
                for(MemberLevel m : memberLevelList) {
                    if (m.getMember_level_value() > parentMemberLevel.getMember_level_value()) {
                        list.add(m);
                    }
                }
                member.put(MemberLevel.MEMBER_LEVEL_LIST, list);
            }

            member.put(Order.ORDER_LIST, new JSONArray());
        }

        renderSuccessJson(member.removeUnfindable());
    }

    @ActionKey(Url.MEMBER_TEAM_MEMBER_LEVEL_LIST)
    public void teamMemberLevelFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        List<MemberLevel> memberLevelList = memberService.teamMemberLevelFind(model.getMember_id());

        renderSuccessJson(memberLevelList);
    }

//    @ActionKey(Url.MEMBER_SAVE)
//    public void save() {
//        Member model = getParameter(Member.class);
//        User userModel = getParameter(User.class);
//        String request_user_id = getRequest_user_id();
//
//        model.validate(Member.MEMBER_NAME);
//
//        memberService.save(model, userModel, request_user_id);
//
//        renderSuccessJson();
//    }

    @ActionKey(Url.MEMBERL_MEMBER_LEVELL_UPDATE)
    public void memberLevelUpdate() {
        Member model = getParameter(Member.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_ID, Member.MEMBER_LEVEL_ID);

        memberService.teamUpdate(model.getMember_id(), model.getMember_level_id(), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.MEMBER_ADMIN_MEMBER_LEVELL_UPDATE)
    public void adminMemberLevelUpdate() {
        Member model = getParameter(Member.class);
        String request_user_id = getRequest_user_id();

        model.validate(Member.MEMBER_ID, Member.MEMBER_LEVEL_ID);

        memberService.updateByMember_idAndMember_level_id(model.getMember_id(), model.getMember_level_id(), request_user_id);

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

//    @ActionKey(Url.MEMBER_LOGIN)
//    public void login() {
//        User model = getParameter(User.class);
//        String request_user_id = getRequest_user_id();
//
//        Map<String, Object> resultMap = memberService.login(model.getUser_phone(), model.getUser_password(), getPlatform(), getVersion(), getIp_address(), request_user_id);
//
//        renderSuccessJson(resultMap);
//    }

//    @ActionKey(Url.MEMBER_WECHAT_H5_LOGIN)
//    public void weChatH5Login() {
//        User model = getParameter(User.class);
//        String request_user_id = getRequest_user_id();
//
//        Map<String, Object> resultMap = memberService.weChatH5Login(model.getWechat_open_id(), model.getWechat_union_id(), getPlatform(), getVersion(), getIp_address(), request_user_id);
//
//        renderSuccessJson(resultMap);
//    }

    @ActionKey(Url.MEMBER_WECHAT_WX_LOGIN)
    public void weChatWX5Login() {
        Map<String, Object> resultMap = memberService.weChatWXLogin(getAttr(Constant.REQUEST_PARAMETER), getPlatform(), getVersion(), getIp_address());

        renderSuccessJson(resultMap);
    }

}