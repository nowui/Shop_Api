package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderController extends Controller {

    private final OrderService orderService = new OrderService();

    @ActionKey(Url.ORDER_LIST)
    public void list() {
//        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        String request_user_id = getRequest_user_id();

        List<Order> orderListvice = orderService.listByUser_id(request_user_id);

        renderSuccessJson(orderListvice);
    }

    @ActionKey(Url.ORDER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_NUMBER);

        int count = orderService.count(model.getOrder_number());

        List<Order> orderListvice = orderService.list(model.getOrder_number(), getM(), getN());

        renderSuccessJson(count, orderListvice);
    }

    @ActionKey(Url.ORDER_TEAM_LIST)
    public void teamList() {
        String request_user_id = getRequest_user_id();

        List<Member> memberList = orderService.teamList(request_user_id);

        renderSuccessJson(memberList);
    }

    @ActionKey(Url.ORDER_FIND)
    public void find() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.find(model.getOrder_id());

        renderSuccessJson(order.removeUnfindable());
    }

    @ActionKey(Url.ORDER_ADMIN_FIND)
    public void adminFind() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.adminFind(model.getOrder_id());

        renderSuccessJson(order.removeSystemInfo());
    }

    @ActionKey(Url.ORDER_TEAM_FIND)
    public void teamFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        Member member = orderService.teamFind(model.getMember_id());

        renderSuccessJson(member.removeUnfindable());
    }

    @ActionKey(Url.ORDER_TEAM_MEMBER_LEVEL_FIND)
    public void teamMemberLevelFind() {
        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        List<MemberLevel> memberLevelList = orderService.teamMemberLevelFind(model.getMember_id());

        renderSuccessJson(memberLevelList);
    }

    @ActionKey(Url.ORDER_SAVE)
    public void save() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_DELIVERY_NAME, Order.ORDER_DELIVERY_PHONE, Order.ORDER_DELIVERY_ADDRESS, Order.ORDER_MESSAGE, Order.ORDER_PAY_TYPE);

        validate("open_id", "pay_type");

        Map<String, String> result = orderService.save(model, getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson(result);
    }

    @ActionKey(Url.ORDER_DELETE)
    public void delete() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        orderService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ORDER_CHECK)
    public void check() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        validate(Product.PRODUCT_LIST);

        Map<String, Object> resultMap = orderService.check(request_user_id);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.ORDER_CONFIRM)
    public void confirm() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        Order order = orderService.confirm(model.getOrder_id(), request_user_id);

        renderSuccessJson(order.keep(Order.ORDER_NUMBER, Order.ORDER_IS_PAY, Order.ORDER_AMOUNT));
    }

    @ActionKey(Url.ORDER_PAY)
    public void pay() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        validate("open_id", "pay_type");

        Map<String, String> result = orderService.pay(model.getOrder_id(), getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson(result);
    }

}