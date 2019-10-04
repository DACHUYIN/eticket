package com.wechatapp.eticket.member.rocketmqsink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 接收更新订单数据的通道
 *
 * @author virgo.zx
 * @date 2019/10/1 21:56
 */
public interface UpdateEticketInfoSink {

    String INPUTUPDATE = "input-update";

    @Input(UpdateEticketInfoSink.INPUTUPDATE)
    SubscribableChannel inputUpdate();

}
