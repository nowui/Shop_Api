package com.shanghaichuangshi.shop.type;

public enum SceneTypeEnum {

    DISTRIBUTOR("DISTRIBUTOR"),
    MEMBER("MEMBER");

    private String key;

    private SceneTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
