package com.shanghaichuangshi.shop.type;

public enum SceneTypeEnum {

    DISTRIBUTOR("DISTRIBUTOR", "分销商"),
    PLATFORM("PLATFORM", "平台"),
    MEMBER("MEMBER", "会员");

    private String key;
    private String value;

    private SceneTypeEnum(String key, String value) {
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
