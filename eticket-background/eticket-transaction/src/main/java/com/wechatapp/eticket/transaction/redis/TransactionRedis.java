package com.wechatapp.eticket.transaction.redis;

import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.common.util.JsonUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 电子券码的整个交易流程，到确认完成，全部通过redis
 *
 * @author virgo.zx
 * @date 2019/9/14 20:43
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionRedis {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 将电子券信息缓存到redis中去
     *
     * @param eticketInfoDTO
     */
    public void saveEticketInfo(EticketInfoDTO eticketInfoDTO) {
        log.info("把券码的相关信息存到redis中");
        Map<String, Object> redisMap = new HashMap<>();
        String mapKey = eticketInfoDTO.getTicketType().toString() + "-" + DateFormatUtils.YYYY_MM_DD_HH_MM_SS.get().format(new Date());
        // 把mapKey存进去，让redis和MySQL可以互相关联起来
        eticketInfoDTO.setRedisMapKey(mapKey);
        redisMap.put(mapKey, JsonUtils.writeValueAsString(eticketInfoDTO));
        stringRedisTemplate.opsForHash().putAll(RedisConstant.REDIS_TRANSACTION + eticketInfoDTO.getWechatOpenIdSeller(), redisMap);
        log.info("券码的相关信息存储成功");
    }

    /**
     * 发送第一次确认消息时，往redis中插入相应的记录
     *
     * @param transactionId
     * @param rocketMQMessage
     */
    public void saveRocketMQLog(String transactionId, String rocketMQMessage) {
        log.info("向RocketMQ发送第一次确认消息");
        // 把向RokcetMQ发送消息记录到redis，并设置有效期，有效期为3天，和RocketMQ里面的消息有效期保持一致
        stringRedisTemplate.opsForValue().set(transactionId, rocketMQMessage, 3, TimeUnit.DAYS);
        log.info("确认消息发送完毕");
    }

    /**
     * 从redis获取相应的记录，来确认是否需要发送消息
     *
     * @param transactionId
     */
    public Boolean getRocketMQlog(String transactionId) {
        log.info("开始从Redis获取RocketMQ消息记录");
        String str = stringRedisTemplate.opsForValue().get(transactionId);
        return str != null && StringUtils.isNotEmpty(str);
    }
}
