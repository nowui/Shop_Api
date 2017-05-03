package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.shop.service.BillService;

import java.util.List;

public class BillController extends Controller {

    private final BillService billService = new BillService();

    @ActionKey(Url.BILL_LIST)
    public void list() {
        String request_user_id = getRequest_user_id();

        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        List<Bill> billListvice = billService.listByUser_id(request_user_id, getM(), getN());

        renderSuccessJson(billListvice);
    }

    @ActionKey(Url.BILL_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Bill model = getParameter(Bill.class);

        model.validate(Bill.BILL_NAME);

        int count = billService.count(model);

        List<Bill> billListvice = billService.list(model, getM(), getN());

        renderSuccessJson(count, billListvice);
    }

    @ActionKey(Url.BILL_FIND)
    public void find() {
        Bill model = getParameter(Bill.class);

        model.validate(Bill.BILL_ID);

        Bill bill = billService.find(model.getBill_id());

        renderSuccessJson(bill.removeUnfindable());
    }

    @ActionKey(Url.BILL_ADMIN_FIND)
    public void adminFind() {
        Bill model = getParameter(Bill.class);

        model.validate(Bill.BILL_ID);

        Bill bill = billService.find(model.getBill_id());

        renderSuccessJson(bill);
    }

    @ActionKey(Url.BILL_SAVE)
    public void save() {
        Bill model = getParameter(Bill.class);
        String request_user_id = getRequest_user_id();

        model.validate(Bill.BILL_NAME);

        billService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.BILLL_UPDATE)
    public void update() {
        Bill model = getParameter(Bill.class);
        String request_user_id = getRequest_user_id();

        model.validate(Bill.BILL_ID, Bill.BILL_NAME);

        billService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.BILL_DELETE)
    public void delete() {
        Bill model = getParameter(Bill.class);
        String request_user_id = getRequest_user_id();

        model.validate(Bill.BILL_ID);

        billService.delete(model, request_user_id);

        renderSuccessJson();
    }

}