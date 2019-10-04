package com.wechatapp.eticket.transaction.factory;

import com.wechatapp.eticket.core.enums.MemberShipLevelEnum;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeDiscountStrategy;
import com.wechatapp.eticket.transaction.strategy.impl.DiamondDiscountStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.GoldenDiscountStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.NormalDiscountStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.SilverDiscountStrategyImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂，使用单例模式
 * 根据会员所处的等级，计算券码手续费的折扣力度
 *
 */
public class ServiceChargeDiscountStrategyFactory {

    private static ServiceChargeDiscountStrategyFactory factory = new ServiceChargeDiscountStrategyFactory();
    private ServiceChargeDiscountStrategyFactory() {
    }
    private static Map<Byte , IServiceChargeDiscountStrategy> strategyMap = new HashMap<>();
    static{
        strategyMap.put(MemberShipLevelEnum.NORMAL.getValue(), new NormalDiscountStrategyImpl());
        strategyMap.put(MemberShipLevelEnum.SILVER.getValue(), new SilverDiscountStrategyImpl());
        strategyMap.put(MemberShipLevelEnum.GOLDEN.getValue(), new GoldenDiscountStrategyImpl());
        strategyMap.put(MemberShipLevelEnum.DIAMOND.getValue(), new DiamondDiscountStrategyImpl());
    }
    public IServiceChargeDiscountStrategy creator(byte type){
        return strategyMap.get(type);
    }
    public static ServiceChargeDiscountStrategyFactory getInstance(){
        return factory;
    }
}
