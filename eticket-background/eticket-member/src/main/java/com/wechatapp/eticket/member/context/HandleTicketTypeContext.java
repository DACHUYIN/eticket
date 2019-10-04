package com.wechatapp.eticket.member.context;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import com.wechatapp.eticket.member.factory.HandleTicketTypeFactory;
import com.wechatapp.eticket.member.strategy.IHandleTicketTypeStrategy;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 选取哪一个策略，即对哪一个券码种类的表进行操作
 *
 * @author virgo.zx
 * @date 2019/10/1 15:00
 */
@Data
@Component
public class HandleTicketTypeContext {

    private IHandleTicketTypeStrategy strategy;

    public void insertOrUpdateTicektTypeTable(TicketTypeEnum ticketTypeEnum, int sqlMethod, String shardingId, Long orderId, EticketInfoDTO eticketInfo) {
        strategy = HandleTicketTypeFactory.getInstance().creator(ticketTypeEnum);
        strategy.insertOrUpdateTicektTypeTable(ticketTypeEnum, sqlMethod, shardingId, orderId, eticketInfo);
    }
}
