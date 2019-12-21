package com.wechatapp.eticket.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author virgo.zx
 * @date 2019/11/30 16:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotentTokenResponseDTO {

    private String responseCode;

    private String responseMsg;

    private String idempotentToken;

}
