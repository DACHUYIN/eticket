package com.wechatapp.eticket.core.common.constants;

import java.math.BigDecimal;

public class ServiceChargeConstant {

	private ServiceChargeConstant() {
		
	}
	
	//10元以下
	public static final BigDecimal UNDERTEN_SERVICE_CHARGE = new BigDecimal(0.1);
	
	//10元到50元
	public static final BigDecimal UNDERFIFTY_SERVICE_CHARGE = new BigDecimal(0.5);
		
	//50元到100元
	public static final BigDecimal UNDERHUNDRED_SERVICE_CHARGE = new BigDecimal(1);
	
	//100元到1000元
	public static final BigDecimal UNDERTHOUSAND_SERVICE_CHARGE = new BigDecimal(2);
	
	//1000元以上
	public static final BigDecimal UPTHOUSAND_SERVICE_CHARGE = new BigDecimal(5);
}
