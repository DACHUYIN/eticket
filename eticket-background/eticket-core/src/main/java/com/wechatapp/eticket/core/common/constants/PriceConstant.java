package com.wechatapp.eticket.core.common.constants;

import java.math.BigDecimal;

public class PriceConstant {

	private PriceConstant() {
		
	}
	
	//10元以下
	public static final BigDecimal PRICE_TEN = new BigDecimal(10);
		
	//10元到50元
	public static final BigDecimal PRICE_FIFTY = new BigDecimal(50);
			
	//50元到100元
	public static final BigDecimal PRICE_HUNDRED = new BigDecimal(100);
		
	//100元到1000元
	public static final BigDecimal PRICE_THOUSAND = new BigDecimal(1000);
}
