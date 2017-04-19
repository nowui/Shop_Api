package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Fute;
import com.shanghaichuangshi.shop.service.FuteService;

import java.util.List;

public class FuteController extends Controller {

    private final FuteService futeService = new FuteService();

    @ActionKey(Url.FUTE_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Fute model = getParameter(Fute.class);

        model.validate(Fute.FUTE_NAME);

        List<Fute> futeListvice = futeService.list(model, getM(), getN());

        renderSuccessJson(futeListvice);
    }

    @ActionKey(Url.FUTE_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Fute model = getParameter(Fute.class);

        model.validate(Fute.FUTE_NAME);

        int count = futeService.count(model);

        List<Fute> futeListvice = futeService.list(model, getM(), getN());

        renderSuccessJson(count, futeListvice);
    }

    @ActionKey(Url.FUTE_FIND)
    public void find() {
        Fute model = getParameter(Fute.class);

        model.validate(Fute.FUTE_ID);

        Fute fute = futeService.find(model.getFute_id());

        renderSuccessJson(fute.removeUnfindable());
    }

    @ActionKey(Url.FUTE_ADMIN_FIND)
    public void adminFind() {
        Fute model = getParameter(Fute.class);

        model.validate(Fute.FUTE_ID);

        Fute fute = futeService.find(model.getFute_id());

        renderSuccessJson(fute);
    }

    @ActionKey(Url.FUTE_SAVE)
    public void save() {
        Fute model = getParameter(Fute.class);
        String request_user_id = getRequest_user_id();

        model.validate(Fute.FUTE_NAME);

        futeService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.FUTEL_UPDATE)
    public void update() {
        Fute model = getParameter(Fute.class);
        String request_user_id = getRequest_user_id();

        model.validate(Fute.FUTE_ID, Fute.FUTE_NAME);

        futeService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.FUTE_DELETE)
    public void delete() {
        Fute model = getParameter(Fute.class);
        String request_user_id = getRequest_user_id();

        model.validate(Fute.FUTE_ID);

        futeService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.FUTE_EXPORT)
    public void export() {
        render(futeService.export());
    }

}