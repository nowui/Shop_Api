package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.shop.service.DeliveryService;

import java.util.List;
import java.util.Map;

public class DeliveryController extends Controller {

    private final DeliveryService deliveryService = new DeliveryService();

    @ActionKey(Url.DELIVERY_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);
        String request_user_id = getRequest_user_id();

        List<Delivery> deliveryList = deliveryService.list(new Delivery(), request_user_id, getM(), getN());

        renderSuccessJson(deliveryList);
    }

    @ActionKey(Url.DELIVERY_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);
        String request_user_id = getRequest_user_id();

        Delivery model = getParameter(Delivery.class);

        model.validate(Delivery.DELIVERY_NAME);

        int count = deliveryService.count(model, request_user_id);

        List<Delivery> deliveryList = deliveryService.list(model, request_user_id, getM(), getN());

        renderSuccessJson(count, deliveryList);
    }

    @ActionKey(Url.DELIVERY_FIND)
    public void find() {
        Delivery model = getParameter(Delivery.class);

        model.validate(Delivery.DELIVERY_ID);

        Delivery delivery = deliveryService.find(model.getDelivery_id());

        renderSuccessJson(delivery.removeUnfindable());
    }

    @ActionKey(Url.DELIVERY_ADMIN_FIND)
    public void adminFind() {
        Delivery model = getParameter(Delivery.class);

        model.validate(Delivery.DELIVERY_ID);

        Delivery delivery = deliveryService.find(model.getDelivery_id());

        renderSuccessJson(delivery);
    }

    @ActionKey(Url.DELIVERY_SAVE)
    public void save() {
        Delivery model = getParameter(Delivery.class);
        String request_user_id = getRequest_user_id();

        model.validate(Delivery.DELIVERY_NAME);

        Delivery delivery = deliveryService.save(model, request_user_id);

        renderSuccessJson(delivery.keep(Delivery.DELIVERY_IS_DEFAULT, Delivery.DELIVERY_NAME, Delivery.DELIVERY_PHONE, Delivery.DELIVERY_ADDRESS));
    }

    @ActionKey(Url.DELIVERYL_UPDATE)
    public void update() {
        Delivery model = getParameter(Delivery.class);
        String request_user_id = getRequest_user_id();

        model.validate(Delivery.DELIVERY_ID, Delivery.DELIVERY_NAME);

        Delivery delivery = deliveryService.update(model, request_user_id);

        renderSuccessJson(delivery.keep(Delivery.DELIVERY_IS_DEFAULT, Delivery.DELIVERY_NAME, Delivery.DELIVERY_PHONE, Delivery.DELIVERY_ADDRESS));
    }

    @ActionKey(Url.DELIVERY_DELETE)
    public void delete() {
        Delivery model = getParameter(Delivery.class);
        String request_user_id = getRequest_user_id();

        model.validate(Delivery.DELIVERY_ID);

        Delivery delivery = deliveryService.delete(model, request_user_id);

        renderSuccessJson(delivery.keep(Delivery.DELIVERY_IS_DEFAULT, Delivery.DELIVERY_NAME, Delivery.DELIVERY_PHONE, Delivery.DELIVERY_ADDRESS));
    }

}