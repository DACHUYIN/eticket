package com.wechatapp.eticket.transaction.service;

import com.wechatapp.eticket.transaction.dto.IdempotentTokenResponseDTO;

/**
 * @author virgo.zx
 * @date 2019/11/30 16:04
 */
public interface IIdempotentTokenService {

    IdempotentTokenResponseDTO createIdempotentToken();

    void checkIdempotentToken(String token);

}
