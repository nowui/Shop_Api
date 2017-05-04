package com.shanghaichuangshi.shop.type;

public enum BillFlowEnum {

	WAIT("WAIT", "账单待完成"),
	FINISH("FINISH", "账单完成"),
	CANCEL("CANCEL", "账单取消");

	private String key;
	private String value;

	private BillFlowEnum(String key, String value) {
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
