package com.shanghaichuangshi.shop.type;

public enum BillTypeEnum {

	ORDER("ORDER", "订单"),
	COMMISSION("COMMISSION", "佣金"),
	WITHDRAW("WITHDRAW", "取现");

	private String key;
	private String value;

	private BillTypeEnum(String key, String value) {
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
