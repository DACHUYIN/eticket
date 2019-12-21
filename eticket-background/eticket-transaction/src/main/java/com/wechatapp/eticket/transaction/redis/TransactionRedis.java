package com.wechatapp.eticket.transaction.redis;

import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.common.util.JsonUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.OrderTypeEnum;
import com.wechatapp.eticket.core.enums.TicketTypeEnum;
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
     * 将电子券信息保存到发布该券的用户名下
     *
     * @param eticketInfoDTO
     */
    public void saveEticketInfo(EticketInfoDTO eticketInfoDTO) {
        log.info("用户{}名下开始暂存券码信息", OrderTypeEnum.SELL.toString().equals(eticketInfoDTO.getOrderType().toString()) ?
                eticketInfoDTO.getWechatOpenIdSeller() : eticketInfoDTO.getWechatOpenIdBuyer());
        Map<String, Object> redisMap = new HashMap<>();
        String mapKey = eticketInfoDTO.getTicketType().toString() + "-" + DateFormatUtils.YYYY_MM_DD_HH_MM_SS.get().format(new Date());
        // 把mapKey存进去，让redis和MySQL可以互相关联起来
        eticketInfoDTO.setRedisMapKey(mapKey);
        redisMap.put(mapKey, JsonUtils.writeValueAsString(eticketInfoDTO));
        stringRedisTemplate.opsForHash().putAll(RedisConstant.REDIS_TRANSACTION + eticketInfoDTO.getWechatOpenIdSeller(), redisMap);
        log.info("券码的相关信息存储到用户名下成功");
    }

    /**
     * 将电子券信息缓存到买卖市场中去
     *
     * @param eticketInfoDTO
     */
    public void saveEticketToBussinessMarket(EticketInfoDTO eticketInfoDTO) {
        log.info("开始把电子券投向{}市场", OrderTypeEnum.SELL.toString().equals(eticketInfoDTO.getOrderType().toString()) ?
                CommonConstant.STR_SELLER : CommonConstant.STR_BUYER);


        log.info("成功在{}市场投入电子券", OrderTypeEnum.SELL.toString().equals(eticketInfoDTO.getOrderType().toString()) ?
                CommonConstant.STR_SELLER : CommonConstant.STR_BUYER);
    }

    /**
     * 将电子券信息保存到券码的分类市场中去
     *
     * @param eticketInfoDTO
     */
    public void saveEticketToClassficationMarket(EticketInfoDTO eticketInfoDTO) {
        // 券码种类
        TicketTypeEnum ticketType = eticketInfoDTO.getTicketType();
        log.info("开始把电子券投向{}市场", ticketType.toString());
        // 存到redis中的key
        String key = getMarketClassification(ticketType);
        // value = wechatOpenId + redisMapKey构成，商品列表只存主键
        // 到时候展示的时候根据主键去每个用户缓存下寻找券码的详细信息返回给前台
        String value = OrderTypeEnum.SELL.toString().equals(eticketInfoDTO.getOrderType().toString()) ?
                eticketInfoDTO.getWechatOpenIdSeller() : eticketInfoDTO.getWechatOpenIdBuyer() + eticketInfoDTO.getRedisMapKey();
        stringRedisTemplate.opsForList().rightPush(key, value);
        log.info("{}市场投放完毕", ticketType.toString());
    }

    
    public void getTicketList() {

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
        return null != str  && StringUtils.isNotEmpty(str);
    }

    /**
     * 把幂等性token暂存到redis中、
     * @param token
     */
    public void saveIdempotentToken(String token) {
        // 把token暂存到redis，并设置过期时间为60秒
        stringRedisTemplate.opsForValue().set(token, token, 60, TimeUnit.SECONDS);
        log.info("幂等性token:{}成功暂存成功", token);
    }

    /**
     * 从redis中获取幂等性token，校验是否存在
     * @param token
     */
    public Boolean getIdempotentToken(String token) {
        log.info("校验redis中是否存在幂等性token");
        String existToken = stringRedisTemplate.opsForValue().get(token);
        return null != existToken && StringUtils.isNotEmpty(existToken);
    }

    /**
     * 删除redis中的幂等性token
     * @param token
     */
    public Boolean delIdempotentToken(String token) {
        log.info("删除redis中既存的幂等性token");
        return stringRedisTemplate.delete(token);
    }

    /**
     * 获取券码的市场分类
     *
     * @param ticketType
     * @return
     */
    private String getMarketClassification(TicketTypeEnum ticketType) {
       switch (ticketType) {
           case ENTERTAINMENT:
               return RedisConstant.REDIS_MARKET_ENTERTAINMENT;
           case ADMINSION:
               return RedisConstant.REDIS_MARKET_ADMINSION;
           case FILM:
               return RedisConstant.REDIS_MARKET_FILM;
           case CONCERT:
               return RedisConstant.REDIS_MARKET_CONCERT;
           case SHOW:
               return RedisConstant.REDIS_MARKET_SHOW;
           default:
               return RedisConstant.REDIS_MARKET_FOOD;
       }
    }
}
