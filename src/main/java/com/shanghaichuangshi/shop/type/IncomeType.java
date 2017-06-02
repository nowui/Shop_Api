package com.shanghaichuangshi.shop.type;

public enum IncomeType {

    COMMISSION("COMMISSION", "佣金"),
    SALE("SALE", "卖货");

    private String key;
    private String value;

    private IncomeType(String key, String value) {
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
