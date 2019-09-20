package com.wechatapp.eticket.core.enums;

/**
 * 标识会员等级信息
 * 根据会员实体类里面的信用评分来区分会员等级
 * 0~99分：普通会员
 * 100~499分：白银会员
 * 500~999分：黄金会员
 * 1000分以上：钻石会员
 * 
 * @author MSI
 *
 */
public enum MemberShipLevelEnum {

	NORMAL(1, "普通会员"), SILVER(2, "白银会员"), GOLDEN(3, "黄金会员"), DIAMOND(4, "钻石会员");

	private Byte value;
	private String description;

	MemberShipLevelEnum(int value, String description) {
		this.value = (byte) value;
		this.description = description;
	}

	public Byte getValue() {
		return this.value;
	}

	public String getName() {
		return this.description;
	}

	public static MemberShipLevelEnum valueOf(int value) {
		for(MemberShipLevelEnum type : MemberShipLevelEnum.values()) {
			if(type.getValue() == (byte) value) {
				return type;
			}
		}
		return null;
	}
}
