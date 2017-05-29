package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.shop.service.ExpressService;

import java.util.List;

public class ExpressController extends Controller {

    private final ExpressService expressService = new ExpressService();

    @ActionKey(Url.EXPRESS_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Express model = getParameter(Express.class);

        model.validate(Express.ORDER_ID);

        List<Express> expressListvice = expressService.list(model.getOrder_id(), getM(), getN());

        renderSuccessJson(expressListvice);
    }

    @ActionKey(Url.EXPRESS_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Express model = getParameter(Express.class);

        model.validate(Express.ORDER_ID);

        int count = expressService.count(model.getOrder_id());

        List<Express> expressListvice = expressService.list(model.getOrder_id(), getM(), getN());

        renderSuccessJson(count, expressListvice);
    }

    @ActionKey(Url.EXPRESS_FIND)
    public void find() {
        Express model = getParameter(Express.class);

        model.validate(Express.EXPRESS_ID);

        Express express = expressService.find(model.getExpress_id());

        renderSuccessJson(express.removeUnfindable());
    }

    @ActionKey(Url.EXPRESS_ADMIN_FIND)
    public void adminFind() {
        Express model = getParameter(Express.class);

        model.validate(Express.EXPRESS_ID);

        Express express = expressService.find(model.getExpress_id());

        renderSuccessJson(express.removeSystemInfo());
    }

    @ActionKey(Url.EXPRESS_SAVE)
    public void save() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.ORDER_ID);

        expressService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.EXPRESSL_UPDATE)
    public void update() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.EXPRESS_ID, Express.ORDER_ID);

        expressService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.EXPRESS_DELETE)
    public void delete() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.EXPRESS_ID);

        expressService.delete(model, request_user_id);

        renderSuccessJson();
    }

}