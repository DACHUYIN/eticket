package com.wechatapp.eticket.transaction.rocketmqsource;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 更新订单数据的通道
 *
 * @author virgo.zx
 * @date 2019/10/1 21:48
 */
public interface UpdateEticketInfoSource {

    String OUTPUTUPDATE = "output-update";

    @Output(UpdateEticketInfoSource.OUTPUTUPDATE)
    MessageChannel outputUpdate();
}
