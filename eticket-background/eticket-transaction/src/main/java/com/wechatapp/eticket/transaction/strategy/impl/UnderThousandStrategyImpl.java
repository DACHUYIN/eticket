package com.wechatapp.eticket.transaction.strategy.impl;

import com.wechatapp.eticket.core.common.constants.ServiceChargeConstant;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeStrategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class UnderThousandStrategyImpl implements IServiceChargeStrategy {

    @Override
    public BigDecimal CalcServiceCharge() {
        return ServiceChargeConstant.UNDERTHOUSAND_SERVICE_CHARGE;
    }
}
