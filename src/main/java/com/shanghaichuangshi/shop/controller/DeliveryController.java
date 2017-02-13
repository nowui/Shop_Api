package com.shanghaichuangshi.shop.controller;

import com.shanghaichuangshi.annotation.Path;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.shop.service.DeliveryService;

import java.util.List;

public class DeliveryController extends Controller {

    private final DeliveryService deliveryService = new DeliveryService();

    @Path(Url.DELIVERY_LIST)
    public void list() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_NAME, Delivery.PAGE_INDEX, Delivery.PAGE_SIZE);

        List<Delivery> deliveryList = deliveryService.list(deliveryModel);

        renderJson(deliveryList);
    }

    @Path(Url.DELIVERY_ADMIN_LIST)
    public void adminList() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.PAGE_INDEX, Delivery.PAGE_SIZE);

        int count = deliveryService.count(deliveryModel);

        List<Delivery> deliveryList = deliveryService.list(deliveryModel);

        renderJson(count, deliveryList);
    }

    @Path(Url.DELIVERY_FIND)
    public void find() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_ID);

        Delivery delivery = deliveryService.find(deliveryModel);

        delivery.removeUnfindable();

        renderJson(delivery);
    }

    @Path(Url.DELIVERY_ADMIN_FIND)
    public void adminFind() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_ID);

        Delivery delivery = deliveryService.find(deliveryModel);

        renderJson(delivery);
    }

    @Path(Url.DELIVERY_SAVE)
    public void save() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_NAME);

        deliveryService.save(deliveryModel);

        renderJson("");
    }

    @Path(Url.DELIVERYL_UPDATE)
    public void update() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_ID, Delivery.DELIVERY_NAME);

        deliveryService.update(deliveryModel);

        renderJson("");
    }

    @Path(Url.DELIVERY_DELETE)
    public void delete() {
        Delivery deliveryModel = getModel(Delivery.class);

        deliveryModel.validate(Delivery.DELIVERY_ID);

        deliveryService.delete(deliveryModel);

        renderJson("");
    }

}