package com.shanghaichuangshi.shop.type;

public enum PayTypeEnum {

    ALIPAY("ALIPAY"),
    WECHAT("WECHAT");

    private String key;

    private PayTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
