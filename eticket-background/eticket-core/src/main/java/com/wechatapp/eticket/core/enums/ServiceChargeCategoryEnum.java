package com.wechatapp.eticket.core.enums;

/**
 * 价格类别信息
 * @author MSI
 *
 */
public enum ServiceChargeCategoryEnum {

	UNDERTEN(1, "10块以下"), UNDFIFTY(2, "50块以下"), UNDERHUNDRED(3, "100块以下"), UNDERTHOUSAND(4, "1000块以下"), UPTHOUSAND(5, "1000块以上");

	private Byte value;
	private String description;

	ServiceChargeCategoryEnum(int value, String description) {
		this.value = (byte) value;
		this.description = description;
	}

	public Byte getValue() {
		return this.value;
	}

	public String getName() {
		return this.description;
	}

	public static ServiceChargeCategoryEnum valueOf(int value) {
		for(ServiceChargeCategoryEnum type : ServiceChargeCategoryEnum.values()) {
			if(type.getValue() == (byte) value) {
				return type;
			}
		}
		return null;
	}
}
