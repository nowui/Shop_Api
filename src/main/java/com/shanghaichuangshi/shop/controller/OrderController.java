package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.service.OrderService;

import java.util.List;

public class OrderController extends Controller {

    private static final OrderService orderService = new OrderService();

    @ActionKey(Url.ORDER_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_NUMBER);

        List<Order> orderListvice = orderService.list(model, getM(), getN());

        renderSuccessJson(orderListvice);
    }

    @ActionKey(Url.ORDER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_NUMBER);

        int count = orderService.count(model);

        List<Order> orderListvice = orderService.list(model, getM(), getN());

        renderSuccessJson(count, orderListvice);
    }

    @ActionKey(Url.ORDER_FIND)
    public void find() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.find(model.getOrder_id());

        order.removeUnfindable();

        renderSuccessJson(order);
    }

    @ActionKey(Url.ORDER_ADMIN_FIND)
    public void adminFind() {
        Order model = getParameter(Order.class);

        model.validate(Order.ORDER_ID);

        Order order = orderService.find(model.getOrder_id());

        renderSuccessJson(order);
    }

    @ActionKey(Url.ORDER_SAVE)
    public void save() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_DELIVERY_NAME, Order.ORDER_DELIVERY_PHONE, Order.ORDER_DELIVERY_ADDRESS, Order.ORDER_MESSAGE, Order.ORDER_PAY_TYPE);

        orderService.save(model, getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ORDERL_UPDATE)
    public void update() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID, Order.ORDER_NUMBER);

        orderService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.ORDER_DELETE)
    public void delete() {
        Order model = getParameter(Order.class);
        String request_user_id = getRequest_user_id();

        model.validate(Order.ORDER_ID);

        orderService.delete(model, request_user_id);

        renderSuccessJson();
    }

}