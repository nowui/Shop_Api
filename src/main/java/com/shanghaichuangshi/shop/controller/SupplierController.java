package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Supplier;
import com.shanghaichuangshi.shop.service.SupplierService;

import java.util.List;

public class SupplierController extends Controller {

    private final SupplierService supplierService = new SupplierService();

    @ActionKey(Url.SUPPLIER_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Supplier model = getParameter(Supplier.class);

        model.validate(Supplier.SUPPLIER_NAME);

        List<Supplier> supplierListvice = supplierService.list(model, getM(), getN());

        renderSuccessJson(supplierListvice);
    }

    @ActionKey(Url.SUPPLIER_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Supplier model = getParameter(Supplier.class);

        model.validate(Supplier.SUPPLIER_NAME);

        int count = supplierService.count(model);

        List<Supplier> supplierListvice = supplierService.list(model, getM(), getN());

        renderSuccessJson(count, supplierListvice);
    }

    @ActionKey(Url.SUPPLIER_FIND)
    public void find() {
        Supplier model = getParameter(Supplier.class);

        model.validate(Supplier.SUPPLIER_ID);

        Supplier supplier = supplierService.find(model.getSupplier_id());

        renderSuccessJson(supplier.removeUnfindable());
    }

    @ActionKey(Url.SUPPLIER_ADMIN_FIND)
    public void adminFind() {
        Supplier model = getParameter(Supplier.class);

        model.validate(Supplier.SUPPLIER_ID);

        Supplier supplier = supplierService.find(model.getSupplier_id());

        renderSuccessJson(supplier);
    }

    @ActionKey(Url.SUPPLIER_SAVE)
    public void save() {
        Supplier model = getParameter(Supplier.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Supplier.SUPPLIER_NAME);
        userModel.validate(User.USER_ACCOUNT, User.USER_PASSWORD);

        supplierService.save(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.SUPPLIERL_UPDATE)
    public void update() {
        Supplier model = getParameter(Supplier.class);
        User userModel = getParameter(User.class);
        String request_user_id = getRequest_user_id();

        model.validate(Supplier.SUPPLIER_ID, Supplier.SUPPLIER_NAME);
        userModel.validate(User.USER_ACCOUNT, User.USER_PASSWORD);

        supplierService.update(model, userModel, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.SUPPLIER_DELETE)
    public void delete() {
        Supplier model = getParameter(Supplier.class);
        String request_user_id = getRequest_user_id();

        model.validate(Supplier.SUPPLIER_ID);

        supplierService.delete(model, request_user_id);

        renderSuccessJson();
    }

}