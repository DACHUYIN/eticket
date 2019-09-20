package com.wechatapp.eticket.transaction.strategy.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.wechatapp.eticket.core.common.constants.ServiceChargeConstant;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeStrategy;

@Service
public class UpThousandStrategyImpl implements IServiceChargeStrategy {

	@Override
	public BigDecimal CalcServiceCharge() {
		return ServiceChargeConstant.UPTHOUSAND_SERVICE_CHARGE;
	}

}
