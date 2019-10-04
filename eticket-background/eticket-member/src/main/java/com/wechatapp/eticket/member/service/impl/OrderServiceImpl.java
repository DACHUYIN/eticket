package com.wechatapp.eticket.member.service.impl;

import com.wechatapp.eticket.core.common.util.ShardingUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.entity.OrderEntity;
import com.wechatapp.eticket.core.entity.PaymentEntity;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.core.repository.FoodTicketRepository;
import com.wechatapp.eticket.core.repository.OrderRepository;
import com.wechatapp.eticket.core.repository.PaymentRepository;
import com.wechatapp.eticket.member.context.HandleTicketTypeContext;
import com.wechatapp.eticket.member.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    private final HandleTicketTypeContext handleTicketTypeContext;

    /**
     * 将会员微服务发送过来的订单数据持久化到MySQL中去
     *
     * @param eticketInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertEticketInfo(EticketInfoDTO eticketInfo) {
        log.info("开始进行持久化处理");
        // 插入订单表
        String shardingId = ShardingUtils.generateShardingId(eticketInfo.getTelephoneNumber());
        OrderEntity orderEntity = OrderEntity.builder()
                .shardingId(shardingId)
                .telephoneNumber(eticketInfo.getTelephoneNumber())
                .redisMapKey(eticketInfo.getRedisMapKey())
                .wechatOpenIdBuyer(eticketInfo.getWechatOpenIdBuyer() != null ? eticketInfo.getWechatOpenIdBuyer() : "")
                .wechatOpenIdSeller(eticketInfo.getWechatOpenIdSeller() != null ? eticketInfo.getWechatOpenIdSeller() : "")
                .orderType(eticketInfo.getOrderType())
                .orderStatus(eticketInfo.getOrderStatus())
                .ticketType(eticketInfo.getTicketType())
                .ticketName(eticketInfo.getTicketName())
                .price(eticketInfo.getPrice())
                .serviceCharge(eticketInfo.getServiceCharge())
                .totalPrice(eticketInfo.getTotalPrice())
                .build();
        // 通过买家或者卖家的wechatOpenId和redisMapKey来获取唯一的订单，检查订单是否存在
        Optional<OrderEntity> isExistOrder = eticketInfo.getWechatOpenIdBuyer() != null ?
                orderRepository.getExistBuyerOrder(eticketInfo.getRedisMapKey(), eticketInfo.getWechatOpenIdBuyer()) :
                orderRepository.getExistSellOrder(eticketInfo.getRedisMapKey(), eticketInfo.getWechatOpenIdSeller());
        // 当订单不存在的时候才需要插入数据库
        if (!isExistOrder.isPresent())
            orderRepository.saveAndFlush(orderEntity);
        // 当订单已经存在的话获取既存订单的订单编号
        Long orderId = isExistOrder.isPresent() ? isExistOrder.get().getOrderId() : orderRepository.getOrderId(shardingId);
        log.info("order表处理完毕，对应的orderId为{}", orderId);
        // 插入对应的订单类型表，例如FOOD,ENTERTAINMENT等等
        handleTicketTypeContext.insertOrUpdateTicektTypeTable(eticketInfo.getTicketType(), eticketInfo.getSqlMethod(), shardingId, orderId, eticketInfo);
        log.info("各种类型的票券表处理完毕");
    }

    /**
     * 更新MySQL中的订单数据
     *
     * @param eticketInfo
     */
    @Override
    public void updateEticketInfo(EticketInfoDTO eticketInfo) {

        PaymentEntity paymentEntity = PaymentEntity.builder()
                //.shardingId(shardingId)
                //.orderId(orderId)
                .wechatOpenIdBuyer(eticketInfo.getWechatOpenIdBuyer() != null ? eticketInfo.getWechatOpenIdBuyer() : "")
                .wechatOpenIdSeller(eticketInfo.getWechatOpenIdSeller() != null ? eticketInfo.getWechatOpenIdSeller() : "")
                .price(eticketInfo.getPrice())
                .serviceCharge(eticketInfo.getServiceCharge())
                .totalPrice(eticketInfo.getTotalPrice())
                .paymentStatus(eticketInfo.getPaymentStatus())
                .build();
        paymentRepository.save(paymentEntity);
        log.info("payment表处理完毕");
    }
}
