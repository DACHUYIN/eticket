package com.wechatapp.eticket.transaction.strategy.impl;

import com.wechatapp.eticket.core.common.constants.MemberShipDiscountConstant;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeDiscountStrategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class GoldenDiscountStrategyImpl implements IServiceChargeDiscountStrategy {

	@Override
	public BigDecimal CalcDiscountCharge(BigDecimal originalServiceCharge) {
		BigDecimal currentCharge = originalServiceCharge.multiply(MemberShipDiscountConstant.GOLDEN_DISCOUNT);
		return currentCharge;
	}

}
