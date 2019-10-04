package com.wechatapp.eticket.transaction.listener;

import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.transaction.redis.TransactionRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * 监听发送至会员微服务的消息（插入相关订单数据）
 *
 * @author virgo.zx
 * @date 2019/8/18 21:46
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "tx-insert-eticketinfo-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InsertEticketInfoListener implements RocketMQLocalTransactionListener {

    private final TransactionRedis transactionRedis;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        String rocketMQMessage = (String) headers.get(RedisConstant.REDIS_ROCKETMQMESSAGE);

        try {
            // 发送第一次消息时往Redis中插入相应的记录
            transactionRedis.saveRocketMQLog(transactionId, rocketMQMessage);
            log.info("发送第一次消息成功");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        // 发送第二次半消息进行确认是否需要发送消息
        boolean isNeedCommit = transactionRedis.getRocketMQlog(transactionId);
        if (isNeedCommit) {
            log.info("发送第二次半消息确认：需要发送消息，并成功发送");
            return RocketMQLocalTransactionState.COMMIT;
        }

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
