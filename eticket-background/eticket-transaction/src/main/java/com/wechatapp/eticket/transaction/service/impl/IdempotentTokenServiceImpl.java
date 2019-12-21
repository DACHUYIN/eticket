package com.wechatapp.eticket.transaction.service.impl;

import com.wechatapp.eticket.core.common.constants.RedisConstant;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.common.exception.IdempotentSecurityException;
import com.wechatapp.eticket.core.common.util.RandomUtils;
import com.wechatapp.eticket.transaction.dto.IdempotentTokenResponseDTO;
import com.wechatapp.eticket.transaction.redis.TransactionRedis;
import com.wechatapp.eticket.transaction.service.IIdempotentTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生成幂等性token，并校验
 *
 * @author virgo.zx
 * @date 2019/11/30 16:07
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IdempotentTokenServiceImpl implements IIdempotentTokenService {

    private final TransactionRedis transactionRedis;

    /**
     * 创建token
     * @return
     */
    @Override
    public IdempotentTokenResponseDTO createIdempotentToken() {
        try {
            String token = RandomUtils.UUID32();
            log.info("把idempotentToken保存到redis中");
            transactionRedis.saveIdempotentToken(token);
            return IdempotentTokenResponseDTO.builder()
                    .idempotentToken(token)
                    .responseCode(ResponseMsgConstant.RESPONSECODE_SUCCESS_IDEMPOTENTTOKEN)
                    .responseMsg(ResponseMsgConstant.RESPONSEMSG_SUCCESS_IDEMPOTENTTOKEN)
                    .build();
        } catch (Exception e) {
            return IdempotentTokenResponseDTO.builder()
                    .responseCode(ResponseMsgConstant.RESPONSECODE_ERROR_IDEMPOTENTTOKEN)
                    .responseMsg(ResponseMsgConstant.RESPONSEMSG_ERROR_IDEMPOTENTTOKEN)
                    .build();
        }
    }

    /**
     * 验证幂等性token
     * @param token
     */
    @Override
    public void checkIdempotentToken(String token) {
        if(StringUtils.isBlank(token)) {
            throw new IdempotentSecurityException("IdempotentToken不合法");
        }

        if(!transactionRedis.getIdempotentToken(token)) {
            throw new IdempotentSecurityException("请勿重复操作");
        }

        if(!transactionRedis.delIdempotentToken(token)) {
            throw new IdempotentSecurityException("请勿重复操作");
        }
    }
}
