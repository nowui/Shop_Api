package com.shanghaichuangshi.shop.type;

public enum PayTypeEnum {

    ALIPAY("ALIPAY", "支付宝"),
    WECHAT("WECHAT", "微信");

    private String key;
    private String value;

    private PayTypeEnum(String key, String value) {
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
