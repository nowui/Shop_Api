package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Bill;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.service.BillService;
import com.shanghaichuangshi.shop.type.BillTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillController extends Controller {

    private final BillService billService = new BillService();

    @ActionKey(Url.BILL_LIST)
    public void list() {
        String request_user_id = getRequest_user_id();

        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Member.MEMBER_WITHDRAW_AMOUNT, billService.findBill_AmountByUser_idAndBill_type(request_user_id, BillTypeEnum.WITHDRAW.getKey()));
        resultMap.put(Member.MEMBER_COMMISSION_AMOUNT, billService.findBill_AmountByUser_idAndBill_type(request_user_id, BillTypeEnum.COMMISSION.getKey()));
        resultMap.put(Member.MEMBER_ORDER_AMOUNT, billService.findBill_AmountByUser_idAndBill_type(request_user_id, BillTypeEnum.ORDER.getKey()));

        List<Bill> billList = billService.listByUser_id(request_user_id, getM(), getN());
        resultMap.put(Bill.BILL_LIST, billList);

        renderSuccessJson(resultMap);
    }

    @ActionKey(Url.BILL_MEMBER_LIST)
    public void memberList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Member model = getParameter(Member.class);

        model.validate(Member.MEMBER_ID);

        List<Bill> billListvice = billService.listByMember_id(model.getMember_id(), getM(), getN());

        renderSuccessJson(billListvice);
    }

    @ActionKey(Url.BILL_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Bill model = getParameter(Bill.class);

        model.validate(Bill.BILL_NAME);

        int count = billService.count(model.getBill_name());

        List<Bill> billListvice = billService.list(model.getBill_name(), getM(), getN());

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

        renderSuccessJson(bill.removeSystemInfo());
    }

}