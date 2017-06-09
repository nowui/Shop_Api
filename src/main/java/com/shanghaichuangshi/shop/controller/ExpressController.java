package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.Kdniao;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.service.ExpressService;
import com.shanghaichuangshi.shop.service.MemberService;
import com.shanghaichuangshi.shop.service.OrderService;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.DateUtil;

import java.util.*;

public class ExpressController extends Controller {

    private final ExpressService expressService = new ExpressService();
    private final OrderService orderService = new OrderService();
    private final MemberService memberService = new MemberService();

    @ActionKey(Url.EXPRESS_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Express model = getParameter(Express.class);

        int count = expressService.count(model.getExpress_number());

        List<Express> expressListvice = expressService.list(model.getExpress_number(), getM(), getN());

        renderSuccessJson(count, expressListvice);
    }

    @ActionKey(Url.EXPRESS_ADMIN_FIND)
    public void adminFind() {
        Express model = getParameter(Express.class);

        model.validate(Express.EXPRESS_ID);

        Express express = expressService.find(model.getExpress_id());

        renderSuccessJson(express.removeSystemInfo());
    }

    @ActionKey(Url.EXPRESS_ADMIN_SAVE)
    public void adminSave() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.ORDER_ID, Express.EXPRESS_TYPE, Express.EXPRESS_NUMBER);

        expressService.save(model, request_user_id);

        orderService.updateReceive(model.getOrder_id(), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.EXPRESSL_ADMIN_UPDATE)
    public void adminUpdate() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.EXPRESS_ID, Express.ORDER_ID);

        expressService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.EXPRESS_ADMIN_DELETE)
    public void adminDelete() {
        Express model = getParameter(Express.class);
        String request_user_id = getRequest_user_id();

        model.validate(Express.EXPRESS_ID);

        expressService.delete(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.EXPRESS_PUSH)
    public void push() {
//        Map<String, Object> resultMap = expressService.push(getPara("RequestData"));
        String requestData = getPara("RequestData");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("EBusinessID", Kdniao.EBusinessID);
        resultMap.put("UpdateTime", DateUtil.getDateTimeString(new Date()));
        resultMap.put("Success", true);
        resultMap.put("Reason", "");

        JSONObject requestDataObject = JSONObject.parseObject(requestData);
        String eBusinessID = requestDataObject.getString("EBusinessID");
        if (!eBusinessID.equals(Kdniao.EBusinessID)) {
            resultMap.put("Success", false);
            resultMap.put("Reason", "EBusinessID is error");

            renderSuccessJson(resultMap);

            return;
        }

        JSONArray jsonArray = JSONArray.parseArray(requestDataObject.getString("Data"));

        List<Express> expressList = new ArrayList<Express>();

        List<String> orderIdList = new ArrayList<String>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String express_id = jsonObject.getString("CallBack");
            String express_type = jsonObject.getString("ShipperCode");
            String express_number = jsonObject.getString("LogisticCode");

            Boolean success = jsonObject.getBoolean("Success");

            String express_flow = "无轨迹";
            Boolean express_status = false;
            String express_trace = jsonObject.getString("Traces");
            if (success) {
                String state = jsonObject.getString("State");
                if (state.equals("1")) {
                    express_flow = "已揽收";
                } else if (state.equals("2")) {
                    express_flow = "在途中";
                } else if (state.equals("201")) {
                    express_flow = "到达派件城市";
                } else if (state.equals("3")) {
                    express_flow = "签收";

                    express_status = true;
                } else if (state.equals("4")) {
                    express_flow = "问题件";
                }

                express_trace = jsonObject.getString("Traces");
            }

            Express express = expressService.find(express_id);

            if (!orderIdList.contains(express.getOrder_id())) {
                orderIdList.add(express.getOrder_id());
            }

//            if (express.getExpress_type().equals(express_type) && express.getExpress_number().equals(express_number) && express.getSystem_status()) {
            express.setExpress_flow(express_flow);
            express.setExpress_status(express_status);
            express.setExpress_trace(express_trace);
            express.setExpress_id(express_id);

            expressList.add(express);
//            }
        }

        expressService.updateBusiness(expressList);

        List<String> updateOrderIdList = new ArrayList<String>();
        for (String order_id : orderIdList) {
            List<Express> expressByOrderIdList = expressService.listByOrder_id(order_id);

            Boolean isStatus = true;
            for (Express express : expressByOrderIdList) {
                if (!express.getExpress_status()) {
                    isStatus = false;
                }
            }

            if (isStatus) {
                updateOrderIdList.add(order_id);

                Order order = orderService.find(order_id);
                Member member = memberService.find(order.getMember_id());
                memberService.orderFlowUpdate(member.getMember_id(), OrderFlowEnum.WAIT_SEND.getKey());
            }
        }
        orderService.updateFinish(updateOrderIdList);
    }

}