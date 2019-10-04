package com.wechatapp.eticket.transaction.rocketmqsource;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 新建订单数据的通道
 *
 * @author virgo.zx
 * @date 2019/10/1 21:48
 */
public interface InsertEticketInfoSource {

    String OUTPUTINSERT = "output-insert";

    @Output(InsertEticketInfoSource.OUTPUTINSERT)
    MessageChannel outputInsert();
}
