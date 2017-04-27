package com.shanghaichuangshi.shop.type;

public enum OrderStatusEnum {

    WAIT("WAIT_PAY", "代付款"),
    EXPIRE("EXPIRE", "超时未付款"),
    WAIT_CONFIRM("WAIT_CONFIRM", "已付款，待确认"),
    WAIT_SEND("WAIT_SEND", "代发货"),
    WAIT_RECEIVE("WAIT_RECEIVE", "代收货"),
    FINISH("FINISH", "订单完成"),
    CANCEL("CANCEL", "订单取消");

    private String key;
    private String value;

    private OrderStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
