package com.wechatapp.eticket.member.rocketmqsink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

/**
 * 接收新建订单数据的通道
 *
 * @author virgo.zx
 * @date 2019/10/1 21:56
 */
public interface InsertEticketInfoSink {

    String INPUTINSERT = "input-insert";

    @Input(InsertEticketInfoSink.INPUTINSERT)
    SubscribableChannel inputInsert();

}
