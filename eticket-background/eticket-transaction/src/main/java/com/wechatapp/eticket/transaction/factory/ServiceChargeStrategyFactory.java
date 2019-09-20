package com.wechatapp.eticket.transaction.factory;

import com.wechatapp.eticket.core.enums.ServiceChargeCategoryEnum;
import com.wechatapp.eticket.transaction.strategy.IServiceChargeStrategy;
import com.wechatapp.eticket.transaction.strategy.impl.UnderFiftyStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.UnderHundredStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.UnderTenStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.UnderThousandStrategyImpl;
import com.wechatapp.eticket.transaction.strategy.impl.UpThousandStrategyImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂，使用单例模式
 * 根据券码的价格区间计算手续费
 *
 */
public class ServiceChargeStrategyFactory {
    private static ServiceChargeStrategyFactory factory = new ServiceChargeStrategyFactory();
    private ServiceChargeStrategyFactory() {
    }
    private static Map<Byte , IServiceChargeStrategy> strategyMap = new HashMap<>();
    static{
        strategyMap.put(ServiceChargeCategoryEnum.UNDERTEN.getValue(), new UnderTenStrategyImpl());
        strategyMap.put(ServiceChargeCategoryEnum.UNDFIFTY.getValue(), new UnderFiftyStrategyImpl());
        strategyMap.put(ServiceChargeCategoryEnum.UNDERHUNDRED.getValue(), new UnderHundredStrategyImpl());
        strategyMap.put(ServiceChargeCategoryEnum.UNDERTHOUSAND.getValue(), new UnderThousandStrategyImpl());
        strategyMap.put(ServiceChargeCategoryEnum.UPTHOUSAND.getValue(), new UpThousandStrategyImpl());
    }
    public IServiceChargeStrategy creator(byte type){
        return strategyMap.get(type);
    }
    public static ServiceChargeStrategyFactory getInstance(){
        return factory;
    }
}
