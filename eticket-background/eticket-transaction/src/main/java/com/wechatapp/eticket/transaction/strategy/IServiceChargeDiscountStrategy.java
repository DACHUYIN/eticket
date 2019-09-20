package com.wechatapp.eticket.transaction.strategy;

import java.math.BigDecimal;

/**
 * 根据不同的会员等级手续费的打折力度是不一样的
 * 普通：99折 白银：97折 黄金：95折 钻石：90折
 * @author virgo.zx
 *
 */
public interface IServiceChargeDiscountStrategy {

	BigDecimal CalcDiscountCharge(BigDecimal originalServiceCharge);
}
