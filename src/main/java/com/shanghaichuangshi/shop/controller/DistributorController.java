package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Distributor;
import com.shanghaichuangshi.shop.service.DistributorService;

import java.util.List;

public class DistributorController extends Controller {

    private final DistributorService distributorService = new DistributorService();

    @ActionKey(Url.DISTRIBUTOR_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Distributor model = getParameter(Distributor.class);

        model.validate(Distributor.DISTRIBUTOR_NAME);

        List<Distributor> distributorListvice = distributorService.list(model, getM(), getN());

        renderSuccessJson(distributorListvice);
    }

    @ActionKey(Url.DISTRIBUTOR_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Distributor model = getParameter(Distributor.class);

        model.validate(Distributor.DISTRIBUTOR_NAME);

        int count = distributorService.count(model);

        List<Distributor> distributorListvice = distributorService.list(model, getM(), getN());

        renderSuccessJson(count, distributorListvice);
    }

    @ActionKey(Url.DISTRIBUTOR_FIND)
    public void find() {
        Distributor model = getParameter(Distributor.class);

        model.validate(Distributor.DISTRIBUTOR_ID);

        Distributor distributor = distributorService.find(model.getDistributor_id());

        renderSuccessJson(distributor.removeUnfindable());
    }

    @ActionKey(Url.DISTRIBUTOR_ADMIN_FIND)
    public void adminFind() {
        Distributor model = getParameter(Distributor.class);

        model.validate(Distributor.DISTRIBUTOR_ID);

        Distributor distributor = distributorService.find(model.getDistributor_id());

        renderSuccessJson(distributor);
    }

    @ActionKey(Url.DISTRIBUTOR_SAVE)
    public void save() {
        Distributor model = getParameter(Distributor.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Distributor.DISTRIBUTOR_NAME);
        userModel.validate(User.USER_ACCOUNT, User.USER_PASSWORD);

        distributorService.save(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.DISTRIBUTORL_UPDATE)
    public void update() {
        Distributor model = getParameter(Distributor.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Distributor.DISTRIBUTOR_ID, Distributor.DISTRIBUTOR_NAME);
        userModel.validate(User.USER_ACCOUNT, User.USER_PASSWORD);

        distributorService.update(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.DISTRIBUTOR_DELETE)
    public void delete() {
        Distributor model = getParameter(Distributor.class);
        String request_user_id = getRequest_user_id();

        model.validate(Distributor.DISTRIBUTOR_ID);

        distributorService.delete(model, request_user_id);

        renderSuccessJson();
    }

}