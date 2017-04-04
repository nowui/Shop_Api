package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Landwind;
import com.shanghaichuangshi.shop.service.LandwindService;

import java.util.List;

public class LandwindController extends Controller {

    private final LandwindService landwindService = new LandwindService();

    @ActionKey(Url.LANDWIND_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Landwind model = getParameter(Landwind.class);

        model.validate(Landwind.LANDWIND_NAME);

        List<Landwind> landwindListvice = landwindService.list(model, getM(), getN());

        renderSuccessJson(landwindListvice);
    }

    @ActionKey(Url.LANDWIND_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Landwind model = getParameter(Landwind.class);

        model.validate(Landwind.LANDWIND_NAME);

        int count = landwindService.count(model);

        List<Landwind> landwindListvice = landwindService.list(model, getM(), getN());

        renderSuccessJson(count, landwindListvice);
    }

    @ActionKey(Url.LANDWIND_FIND)
    public void find() {
        Landwind model = getParameter(Landwind.class);

        model.validate(Landwind.LANDWIND_ID);

        Landwind landwind = landwindService.find(model.getLandwind_id());

        renderSuccessJson(landwind.formatToMap());
    }

    @ActionKey(Url.LANDWIND_ADMIN_FIND)
    public void adminFind() {
        Landwind model = getParameter(Landwind.class);

        model.validate(Landwind.LANDWIND_ID);

        Landwind landwind = landwindService.find(model.getLandwind_id());

        renderSuccessJson(landwind);
    }

    @ActionKey(Url.LANDWIND_SAVE)
    public void save() {
        Landwind model = getParameter(Landwind.class);
        String request_user_id = getRequest_user_id();

        model.validate(Landwind.LANDWIND_NAME);

        landwindService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.LANDWINDL_UPDATE)
    public void update() {
        Landwind model = getParameter(Landwind.class);
        String request_user_id = getRequest_user_id();

        model.validate(Landwind.LANDWIND_ID, Landwind.LANDWIND_NAME);

        landwindService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.LANDWIND_DELETE)
    public void delete() {
        Landwind model = getParameter(Landwind.class);
        String request_user_id = getRequest_user_id();

        model.validate(Landwind.LANDWIND_ID);

        landwindService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.LANDWIND_EXPORT)
    public void export() {
        render(landwindService.export());
    }

}