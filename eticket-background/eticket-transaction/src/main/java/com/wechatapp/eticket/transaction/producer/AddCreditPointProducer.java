package com.wechatapp.eticket.transaction.producer;

import com.alibaba.fastjson.JSON;
import com.wechatapp.eticket.core.dto.MemberShipDTO;
import com.wechatapp.eticket.transaction.dto.EticketInfoDTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddCreditPointProducer {

    private final Source source;

    /**
     * 向会员中心发送积分增加的消息
     * @param eticketInfoDTO
     */
    public void sendCreditPointInfo(EticketInfoDTO eticketInfoDTO) {
        // 发送半消息
        String transactionId = UUID.randomUUID().toString();
        this.source.output()
                .send(
                        MessageBuilder
                                .withPayload(
                                        MemberShipDTO.builder()
                                                .wechatOpenIdBuyer(eticketInfoDTO.getWechatOpenIdBuyer())
                                                .wechatOpenIdSeller(eticketInfoDTO.getWechatOpenIdSeller())
                                                .creditPoint(1)
                                                .build()
                                )
                                // header也有妙用...
                                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                                .setHeader("dto", JSON.toJSONString(eticketInfoDTO))
                                .build()
                );
    }
}
