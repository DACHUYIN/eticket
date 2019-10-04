package com.wechatapp.eticket.transaction.producer;

import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.transaction.rocketmqsource.InsertEticketInfoSource;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 生产消息-新建订单数据
 *
 * @author virgo.zx
 * @date 2019/8/19 20:25
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InsertEticketInfoProducer {

    private final InsertEticketInfoSource source;

    /**
     * 向会员微服务发送消息，让其把改订单数据持久化到MySQL中去
     *
     * @param eticketInfoDTO
     */
    public synchronized void produceInsertEticketInfoMessage(EticketInfoDTO eticketInfoDTO) {

        // 出售or求购
        boolean sellerOrBuyer = OrderTypeEnum.SELL.toString().equals(eticketInfoDTO.getOrderType().toString());

        // 出售券码
        if (sellerOrBuyer && "".equals(eticketInfoDTO.getWechatOpenIdSeller())) {
            throw new IllegalArgumentException("参数非法！卖家不存在！");
        }
        // 求购券码
        if (!sellerOrBuyer && "".equals(eticketInfoDTO.getWechatOpenIdBuyer())) {
            throw new IllegalArgumentException("参数非法！买家不存在！");
        }

        // 发送半消息
        String transactionId = RedisConstant.REDIS_ROCKETMQLOG
                + (sellerOrBuyer ? CommonConstant.WECHAT_OPENID_SELLER + eticketInfoDTO.getWechatOpenIdSeller()
                : CommonConstant.WECHAT_OPENID_BUYER + eticketInfoDTO.getWechatOpenIdBuyer()) + "-"
                + DateFormatUtils.YYYY_MM_DD_HH_MM_SS.get().format(new Date());
        log.info("ID为{}开始发送消息", transactionId);
        this.source.outputInsert()
                .send(
                        MessageBuilder
                                .withPayload(eticketInfoDTO)
                                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                                .setHeader(RedisConstant.REDIS_ROCKETMQMESSAGE, sellerOrBuyer ?
                                        String.format("卖家%s出售一张券码，券码的ID为%s", eticketInfoDTO.getWechatOpenIdSeller(), eticketInfoDTO.getRedisMapKey()) :
                                        String.format("买家%s求购一张券码，券码的ID为%s", eticketInfoDTO.getWechatOpenIdBuyer(), eticketInfoDTO.getRedisMapKey()))
                                .build()
                );
    }
}
