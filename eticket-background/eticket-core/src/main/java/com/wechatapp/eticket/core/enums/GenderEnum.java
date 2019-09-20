package com.wechatapp.eticket.core.enums;

public enum GenderEnum {

	UNKNOW(0, "未知"), MAN(1, "先生"), WOMAN(2, "女士");

	private Byte value;
	private String description;

	GenderEnum(int value, String description) {
		this.value = (byte) value;
		this.description = description;
	}

	public Byte getValue() {
		return this.value;
	}

	public String getName() {
		return this.description;
	}
}
