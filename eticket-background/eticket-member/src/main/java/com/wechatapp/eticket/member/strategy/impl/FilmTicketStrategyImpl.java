package com.wechatapp.eticket.member.strategy.impl;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import com.wechatapp.eticket.member.strategy.IHandleTicketTypeStrategy;

/**
 *
 * @author virgo.zx
 * @date 2019/10/6 21:00
 */
public class FilmTicketStrategyImpl implements IHandleTicketTypeStrategy {

    @Override
    public void insertOrUpdateTicektTypeTable(TicketTypeEnum ticketTypeEnum, int sqlMethod, String shardingId, Long orderId, EticketInfoDTO eticketInfo) {

    }
}
