package com.wechatapp.eticket.transaction.producer;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.rocketmqsource.UpdateEticketInfoSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生产消息-更新订单数据
 *
 * @author virgo.zx
 * @date 2019/8/19 20:26
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateEticketInfoProducer {

    private final UpdateEticketInfoSource source;

    /**
     * 向会员微服务发送消息，让其更新相关的订单数据
     *
     * @param eticketInfoDTO
     */
    public synchronized void produceUpdateEticketInfoMessage(EticketInfoDTO eticketInfoDTO) {

    }

}
