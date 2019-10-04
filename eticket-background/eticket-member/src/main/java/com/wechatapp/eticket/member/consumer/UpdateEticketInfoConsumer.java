package com.wechatapp.eticket.member.consumer;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.member.rocketmqsink.UpdateEticketInfoSink;
import com.wechatapp.eticket.member.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * 更新订单数据
 *
 * @author virgo.zx
 * @date 2019/10/1 21:10
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateEticketInfoConsumer {

    private final IOrderService orderService;

    @StreamListener(UpdateEticketInfoSink.INPUTUPDATE)
    public synchronized void receive(EticketInfoDTO message) {
        log.info("更新订单数据-接收生产者生产的消息给消费者使用，该消息为：" + message);
        this.orderService.updateEticketInfo(message);
    }
}
