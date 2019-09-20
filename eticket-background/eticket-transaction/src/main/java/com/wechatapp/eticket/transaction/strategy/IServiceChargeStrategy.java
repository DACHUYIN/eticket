package com.wechatapp.eticket.transaction.strategy;

import java.math.BigDecimal;

/**
 * 在不同的价格区间服务费是不一样的
 * 10块以下：1毛
 * 10块到50块：5毛
 * 50块到100块：1块
 * 100到1000块：2块
 *
 * @author virgo.zx
 * 
 */
public interface IServiceChargeStrategy {

    BigDecimal CalcServiceCharge();
}
