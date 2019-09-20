package com.wechatapp.eticket.transaction.context;

import com.wechatapp.eticket.transaction.factory.ServiceChargeDiscountStrategyFactory;
import com.wechatapp.eticket.transaction.factory.ServiceChargeStrategyFactory;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeDiscountStrategy;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeStrategy;
import lombok.Data;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;


@Data
@Component
public class ServiceChargeContext {

    private IServiceChargeDiscountStrategy memberShipStrategy;

    private IServiceChargeStrategy serviceChargeStrategy;

    //计算真正的手续费
    public BigDecimal CalcServiceCharge(int membershipType, int priceRangeType) {
        memberShipStrategy = ServiceChargeDiscountStrategyFactory.getInstance().creator((byte) membershipType);
        serviceChargeStrategy = ServiceChargeStrategyFactory.getInstance().creator((byte) priceRangeType);
        return  memberShipStrategy.CalcDiscountCharge(serviceChargeStrategy.CalcServiceCharge());
    }
}
