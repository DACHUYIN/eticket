package com.wechatapp.eticket.core.common.constants;

import java.math.BigDecimal;

public class MemberShipDiscountConstant {

	private MemberShipDiscountConstant() {
	}
	
	//普通会员
	public static final BigDecimal NORMAL_DISCOUNT = new BigDecimal(0.99);
	
	//白银会员
	public static final BigDecimal SLIVER_DISCOUNT = new BigDecimal(0.97);
	
	//黄金会员
	public static final BigDecimal GOLDEN_DISCOUNT = new BigDecimal(0.95);
	
	//钻石会员
	public static final BigDecimal DIAMOND_DISCOUNT = new BigDecimal(0.90);
}
