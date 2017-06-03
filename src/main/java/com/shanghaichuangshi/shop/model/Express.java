package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Express extends Model<Express> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "快递编号")
    public static final String EXPRESS_ID = "express_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "订单编号")
    public static final String ORDER_ID = "order_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "快递类型")
    public static final String EXPRESS_TYPE = "express_type";

    @Column(type = ColumnType.VARCHAR, length = 30, comment = "快递单号")
    public static final String EXPRESS_NUMBER = "express_number";

    @Column(type = ColumnType.VARCHAR, length = 250, comment = "快递结果")
    public static final String EXPRESS_RESULT = "express_result";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "快递流程")
    public static final String EXPRESS_FLOW = "express_flow";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "快递状态")
    public static final String EXPRESS_STATUS = "express_status";

    @Column(type = ColumnType.VARCHAR, length = 65535, comment = "快递跟踪")
    public static final String EXPRESS_TRACE = "express_trace";

    public String getExpress_id() {
        return getStr(EXPRESS_ID);
    }

    public void setExpress_id(String express_id) {
        set(EXPRESS_ID, express_id);
    }
    public String getOrder_id() {
        return getStr(ORDER_ID);
    }

    public void setOrder_id(String order_id) {
        set(ORDER_ID, order_id);
    }
    public String getExpress_type() {
        return getStr(EXPRESS_TYPE);
    }

    public void setExpress_type(String express_type) {
        set(EXPRESS_TYPE, express_type);
    }
    public String getExpress_number() {
        return getStr(EXPRESS_NUMBER);
    }

    public void setExpress_number(String express_number) {
        set(EXPRESS_NUMBER, express_number);
    }
    public String getExpress_result() {
        return getStr(EXPRESS_RESULT);
    }

    public void setExpress_result(String express_result) {
        set(EXPRESS_RESULT, express_result);
    }
    public String getExpress_flow() {
        return getStr(EXPRESS_FLOW);
    }

    public void setExpress_flow(String express_flow) {
        set(EXPRESS_FLOW, express_flow);
    }
    public Boolean getExpress_status() {
        return getBoolean(EXPRESS_STATUS);
    }

    public void setExpress_status(Boolean express_status) {
        set(EXPRESS_STATUS, express_status);
    }
    public String getExpress_trace() {
        return getStr(EXPRESS_TRACE);
    }

    public void setExpress_trace(String express_trace) {
        set(EXPRESS_TRACE, express_trace);
    }
}