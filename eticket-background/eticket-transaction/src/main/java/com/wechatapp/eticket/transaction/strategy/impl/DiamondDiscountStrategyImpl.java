package com.wechatapp.eticket.transaction.strategy.impl;

import com.wechatapp.eticket.core.common.constants.MemberShipDiscountConstant;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeDiscountStrategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class DiamondDiscountStrategyImpl implements IServiceChargeDiscountStrategy {

	@Override
	public BigDecimal CalcDiscountCharge(BigDecimal originalServiceCharge) {
		BigDecimal currentCharge = originalServiceCharge.multiply(MemberShipDiscountConstant.DIAMOND_DISCOUNT);
		return currentCharge;
	}

}
