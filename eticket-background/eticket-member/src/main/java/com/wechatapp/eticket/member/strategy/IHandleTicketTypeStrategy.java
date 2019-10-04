package com.wechatapp.eticket.member.strategy;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;

/**
 *
 *
 * @author virgo.zx
 * @date 2019/10/1 13:50
 */
public interface IHandleTicketTypeStrategy {

    void insertOrUpdateTicektTypeTable(TicketTypeEnum ticketTypeEnum, int sqlMethod, String shardingId, Long orderId, EticketInfoDTO eticketInfo);
}
