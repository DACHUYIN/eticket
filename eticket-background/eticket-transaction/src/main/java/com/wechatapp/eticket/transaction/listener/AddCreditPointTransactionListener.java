package com.wechatapp.eticket.transaction.listener;

import com.alibaba.fastjson.JSON;
import com.wechatapp.eticket.core.entity.RocketmqTransactionLogEntity;
import com.wechatapp.eticket.core.enums.PaymentStatusEnum;
import com.wechatapp.eticket.core.repository.PaymentRepository;
import com.wechatapp.eticket.core.repository.RocketmqTransactionLogRepository;
import com.wechatapp.eticket.transaction.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.service.ITransactionService;
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
 * @author virgo.zx
 * @date 2019/8/18 21:46
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "tx-add-creditpoint-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddCreditPointTransactionListener implements RocketMQLocalTransactionListener {

    private final RocketmqTransactionLogRepository rocketmqTransactionLogRepository;

    private final ITransactionService transactionService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        String dtoString = (String) headers.get("dto");
        EticketInfoDTO eticketInfoDTO = JSON.parseObject(dtoString, EticketInfoDTO.class);

        try {
            // 发送第一次消息时往log表插入相应的记录
            this.transactionService.insertRockmqTransactionLog(transactionId, eticketInfoDTO);
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
        RocketmqTransactionLogEntity log = rocketmqTransactionLogRepository.getRocketmqTransactionLog(transactionId);
        if (null != log) {
            return RocketMQLocalTransactionState.COMMIT;
        }

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
