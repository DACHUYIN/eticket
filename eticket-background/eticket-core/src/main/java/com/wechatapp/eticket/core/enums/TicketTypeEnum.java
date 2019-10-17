package com.wechatapp.eticket.core.enums;

public enum TicketTypeEnum {

	FOOD(1, "餐饮券"), ENTERTAINMENT(2, "影音娱乐券"), ADMINSION(3, "景点门票"), FILM(4, "电影票"), CONCERT(5, "演唱会门票"),
	SHOW(6, "展览会门票");

	private Byte value;
	private String description;

	TicketTypeEnum(int value, String description) {
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
