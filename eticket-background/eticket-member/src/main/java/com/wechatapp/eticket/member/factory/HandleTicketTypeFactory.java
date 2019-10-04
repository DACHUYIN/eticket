package com.wechatapp.eticket.member.factory;

import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import com.wechatapp.eticket.member.strategy.IHandleTicketTypeStrategy;
import com.wechatapp.eticket.member.strategy.impl.AdminsionTicketStrategyImpl;
import com.wechatapp.eticket.member.strategy.impl.EntertainmentTicketStrategyImpl;
import com.wechatapp.eticket.member.strategy.impl.FoodTicketStrategyImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂，使用单例模式
 * 根据TicketType,选取插入或更新到哪一张数据库
 *
 * @author virgo.zx
 * @date 2019/10/1 14:26
 */
public class HandleTicketTypeFactory {

    private static HandleTicketTypeFactory factory = new HandleTicketTypeFactory();
    private HandleTicketTypeFactory() {
    }
    private static Map<TicketTypeEnum, IHandleTicketTypeStrategy> strategyMap = new HashMap<>();
    static {
        strategyMap.put(TicketTypeEnum.FOOD, new FoodTicketStrategyImpl());
        strategyMap.put(TicketTypeEnum.ADMINSION, new AdminsionTicketStrategyImpl());
        strategyMap.put(TicketTypeEnum.ENTERTAINMENT, new EntertainmentTicketStrategyImpl());
    }
    public IHandleTicketTypeStrategy creator(TicketTypeEnum ticketTypeEnum) { return strategyMap.get(ticketTypeEnum); }
    public static HandleTicketTypeFactory getInstance() { return factory; }
}
