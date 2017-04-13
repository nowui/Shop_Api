package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Guangfeng;
import com.shanghaichuangshi.shop.service.GuangfengService;

import java.util.List;

public class GuangfengController extends Controller {

    private final GuangfengService guangfengService = new GuangfengService();

    @ActionKey(Url.GUANGFENG_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Guangfeng model = getParameter(Guangfeng.class);

        model.validate(Guangfeng.GUANGFENG_NAME);

        List<Guangfeng> guangfengListvice = guangfengService.list(model, getM(), getN());

        renderSuccessJson(guangfengListvice);
    }

    @ActionKey(Url.GUANGFENG_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Guangfeng model = getParameter(Guangfeng.class);

        model.validate(Guangfeng.GUANGFENG_NAME);

        int count = guangfengService.count(model);

        List<Guangfeng> guangfengListvice = guangfengService.list(model, getM(), getN());

        renderSuccessJson(count, guangfengListvice);
    }

    @ActionKey(Url.GUANGFENG_RESULT_LIST)
    public void resultList() {
        List<Guangfeng> guangfengListvice = guangfengService.resultList();

        renderSuccessJson(guangfengListvice);
    }

    @ActionKey(Url.GUANGFENG_FIND)
    public void find() {
        Guangfeng model = getParameter(Guangfeng.class);

        model.validate(Guangfeng.GUANGFENG_ID);

        Guangfeng guangfeng = guangfengService.find(model.getGuangfeng_id());

        renderSuccessJson(guangfeng.removeUnfindable());
    }

    @ActionKey(Url.GUANGFENG_ADMIN_FIND)
    public void adminFind() {
        Guangfeng model = getParameter(Guangfeng.class);

        model.validate(Guangfeng.GUANGFENG_ID);

        Guangfeng guangfeng = guangfengService.find(model.getGuangfeng_id());

        renderSuccessJson(guangfeng);
    }

    @ActionKey(Url.GUANGFENG_SAVE)
    public void save() {
        Guangfeng model = getParameter(Guangfeng.class);
        String request_user_id = getRequest_user_id();

        model.validate(Guangfeng.GUANGFENG_NAME);

        guangfengService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.GUANGFENGL_UPDATE)
    public void update() {
        Guangfeng model = getParameter(Guangfeng.class);
        String request_user_id = getRequest_user_id();

        model.validate(Guangfeng.GUANGFENG_ID, Guangfeng.GUANGFENG_NAME);

        guangfengService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.GUANGFENG_DELETE)
    public void delete() {
        Guangfeng model = getParameter(Guangfeng.class);
        String request_user_id = getRequest_user_id();

        model.validate(Guangfeng.GUANGFENG_ID);

        guangfengService.delete(model, request_user_id);

        renderSuccessJson();
    }

}