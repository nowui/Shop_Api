package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

import java.math.BigDecimal;

public class Order extends Model<Order> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "订单编号")
    public static final String ORDER_ID = "order_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 15, comment = "订单号")
    public static final String ORDER_NUMBER = "order_number";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "收货人姓名")
    public static final String ORDER_DELIVERY_NAME = "order_delivery_name";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "收货人电话")
    public static final String ORDER_DELIVERY_PHONE = "order_delivery_phone";

    @Column(type = ColumnType.VARCHAR, length = 200, comment = "收货地址")
    public static final String ORDER_DELIVERY_ADDRESS = "order_delivery_address";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "买家留言")
    public static final String ORDER_MESSAGE = "order_message";

    @Column(type = ColumnType.INT, length = 11, comment = "商品数量")
    public static final String ORDER_PRODUCT_NUMBER = "order_product_number";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "应付金额")
    public static final String ORDER_RECEIVABLE_AMOUNT = "order_receivable_amount";

    @Column(type = ColumnType.DECIMAL, length = 0, comment = "实付金额")
    public static final String ORDER_RECEIVE_AMOUNT = "order_receive_amount";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否支付")
    public static final String ORDER_IS_CONFIRM = "order_is_confirm";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否确认")
    public static final String ORDER_IS_PAY = "order_is_pay";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "支付类型")
    public static final String ORDER_PAY_TYPE = "order_pay_type";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "支付号")
    public static final String ORDER_PAY_NUMBER = "order_pay_number";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "支付帐号")
    public static final String ORDER_PAY_ACCOUNT = "order_pay_account";

    @Column(type = ColumnType.VARCHAR, length = 19, comment = "支付时间")
    public static final String ORDER_PAY_TIME = "order_pay_time";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "支付结果")
    public static final String ORDER_PAY_RESULT = "order_pay_result";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员等级编号")
    public static final String MEMBER_LEVEL_ID = "member_level_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "会员等级名称")
    public static final String MEMBER_LEVEL_NAME = "member_level_name";

    @Column(type = ColumnType.INT, length = 11, comment = "会员等级数值")
    public static final String MEMBER_LEVEL_VALUE = "member_level_value";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "订单状态")
    public static final String ORDER_STATUS = "order_status";

    
    public String getOrder_id() {
        return getStr(ORDER_ID);
    }

    public void setOrder_id(String order_id) {
        set(ORDER_ID, order_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getOrder_number() {
        return getStr(ORDER_NUMBER);
    }

    public void setOrder_number(String order_number) {
        set(ORDER_NUMBER, order_number);
    }

    public String getOrder_delivery_name() {
        return getStr(ORDER_DELIVERY_NAME);
    }

    public void setOrder_delivery_name(String order_delivery_name) {
        set(ORDER_DELIVERY_NAME, order_delivery_name);
    }

    public String getOrder_delivery_phone() {
        return getStr(ORDER_DELIVERY_PHONE);
    }

    public void setOrder_delivery_phone(String order_delivery_phone) {
        set(ORDER_DELIVERY_PHONE, order_delivery_phone);
    }

    public String getOrder_delivery_address() {
        return getStr(ORDER_DELIVERY_ADDRESS);
    }

    public void setOrder_delivery_address(String order_delivery_address) {
        set(ORDER_DELIVERY_ADDRESS, order_delivery_address);
    }

    public String getOrder_message() {
        return getStr(ORDER_MESSAGE);
    }

    public void setOrder_message(String order_message) {
        set(ORDER_MESSAGE, order_message);
    }

    public Integer getOrder_product_number() {
        return getInt(ORDER_PRODUCT_NUMBER);
    }

    public void setOrder_product_number(Integer order_product_number) {
        set(ORDER_PRODUCT_NUMBER, order_product_number);
    }

    public BigDecimal getOrder_receivable_amount() {
        return getBigDecimal(ORDER_RECEIVABLE_AMOUNT);
    }

    public void setOrder_receivable_amount(BigDecimal order_receivable_amount) {
        set(ORDER_RECEIVABLE_AMOUNT, order_receivable_amount);
    }

    public BigDecimal getOrder_receive_amount() {
        return getBigDecimal(ORDER_RECEIVE_AMOUNT);
    }

    public void setOrder_receive_amount(BigDecimal order_receive_amount) {
        set(ORDER_RECEIVE_AMOUNT, order_receive_amount);
    }

    public Boolean getOrder_is_confirm() {
        return getBoolean(ORDER_IS_CONFIRM);
    }

    public void setOrder_is_confirm(Boolean order_is_confirm) {
        set(ORDER_IS_CONFIRM, order_is_confirm);
    }

    public Boolean getOrder_is_pay() {
        return getBoolean(ORDER_IS_PAY);
    }

    public void setOrder_is_pay(Boolean order_is_pay) {
        set(ORDER_IS_PAY, order_is_pay);
    }

    public String getOrder_pay_type() {
        return getStr(ORDER_PAY_TYPE);
    }

    public void setOrder_pay_type(String order_pay_type) {
        set(ORDER_PAY_TYPE, order_pay_type);
    }

    public String getOrder_pay_number() {
        return getStr(ORDER_PAY_NUMBER);
    }

    public void setOrder_pay_number(String order_pay_number) {
        set(ORDER_PAY_NUMBER, order_pay_number);
    }

    public String getOrder_pay_account() {
        return getStr(ORDER_PAY_ACCOUNT);
    }

    public void setOrder_pay_account(String order_pay_account) {
        set(ORDER_PAY_ACCOUNT, order_pay_account);
    }

    public String getOrder_pay_time() {
        return getStr(ORDER_PAY_TIME);
    }

    public void setOrder_pay_time(String order_pay_time) {
        set(ORDER_PAY_TIME, order_pay_time);
    }

    public String getOrder_pay_result() {
        return getStr(ORDER_PAY_RESULT);
    }

    public void setOrder_pay_result(String order_pay_result) {
        set(ORDER_PAY_RESULT, order_pay_result);
    }

    public String getMember_level_id() {
        return getStr(MEMBER_LEVEL_ID);
    }

    public void setMember_level_id(String member_level_id) {
        set(MEMBER_LEVEL_ID, member_level_id);
    }

    public String getMember_level_name() {
        return getStr(MEMBER_LEVEL_NAME);
    }

    public void setMember_level_name(String member_level_name) {
        set(MEMBER_LEVEL_NAME, member_level_name);
    }

    public Integer getMember_level_value() {
        return getInt(MEMBER_LEVEL_VALUE);
    }

    public void setMember_level_value(Integer member_level_value) {
        set(MEMBER_LEVEL_VALUE, member_level_value);
    }

    public String getOrder_status() {
        return getStr(ORDER_STATUS);
    }

    public void setOrder_status(String order_status) {
        set(ORDER_STATUS, order_status);
    }
}