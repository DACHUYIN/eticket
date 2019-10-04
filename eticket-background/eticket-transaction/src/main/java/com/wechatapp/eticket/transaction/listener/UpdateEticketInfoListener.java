package com.wechatapp.eticket.transaction.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * 监听发送至会员微服务的消息（更新相关订单数据）
 *
 * @author virgo.zx
 * @date 2019/8/19 20:27
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "tx-update-eticketinfo-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateEticketInfoListener implements RocketMQLocalTransactionListener {


    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return null;
    }
}
