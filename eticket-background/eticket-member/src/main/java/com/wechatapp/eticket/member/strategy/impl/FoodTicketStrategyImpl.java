package com.wechatapp.eticket.member.strategy.impl;

import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.entity.FoodTicketEntity;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
import com.wechatapp.eticket.core.repository.FoodTicketRepository;
import com.wechatapp.eticket.member.strategy.IHandleTicketTypeStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

/**
 * 处理FoodTicketEntity,插入新数据或更新既有数据
 *
 * @author virgo.zx
 * @date 2019/10/1 13:55
 */
@Service
@Slf4j
public class FoodTicketStrategyImpl implements IHandleTicketTypeStrategy {

    @Autowired
    private FoodTicketRepository foodTicketRepository;

    @Override
    public void insertOrUpdateTicektTypeTable(TicketTypeEnum ticketTypeEnum, int sqlMethod, String shardingId, Long orderId, EticketInfoDTO eticketInfo) {
        log.info("进去FoodTicket策略类");
        try {
            FoodTicketEntity foodTicketEntity = makeFoodEntity(eticketInfo, shardingId, orderId);
            switch (sqlMethod) {
                case CommonConstant.SQL_METHOD_INSERT:
                    log.info("往FoodTicketEntity表做插入处理");
                    Optional<FoodTicketEntity> isExistFoodEntity = foodTicketRepository.getExistFoodTikcet(orderId);
                    // 当不存在既有订单的时候才需要做插入处理
                    if (!isExistFoodEntity.isPresent())
                        foodTicketRepository.save(foodTicketEntity);
                    break;
                case CommonConstant.SQL_METHOD_UPDATE:
                    log.info("往FoodTicketEntity表做更新处理");
                    break;
                default:
                    break;
            }
        } catch (ParseException e) {
            log.error(e.toString());
        }
    }

    /**
     * 构建FoodTicketEntity实体类
     *
     * @param eticketInfoDTO
     * @param shardingId
     * @param orderId
     * @return
     */
    private FoodTicketEntity makeFoodEntity(EticketInfoDTO eticketInfoDTO, String shardingId, Long orderId) throws ParseException {
        log.info("构建FoodTicketEntity实体类");
        return FoodTicketEntity.builder()
                .shardingId(shardingId)
                .orderId(orderId)
                .telephoneNumber(eticketInfoDTO.getTelephoneNumber())
                .ticketName(eticketInfoDTO.getTicketName())
                .price(eticketInfoDTO.getPrice())
                .serviceCharge(eticketInfoDTO.getServiceCharge())
                .totalPrice(eticketInfoDTO.getTotalPrice())
                .uploadFlag(eticketInfoDTO.getUploadFlag())
                .imgAddress(eticketInfoDTO.getImgAddress())
                .qrCode(eticketInfoDTO.getQrCode())
                .startDate(DateFormatUtils.YYYY_MM_DD.get().parse(eticketInfoDTO.getStartDate()))
                .endDate(DateFormatUtils.YYYY_MM_DD.get().parse(eticketInfoDTO.getEndDate()))
                .termValidity(eticketInfoDTO.getTermValidity())
                .build();
    }
}
