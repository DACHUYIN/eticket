package com.wechatapp.eticket.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author virgo.zx
 * @date 2019/9/14 20:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {

    /**
     * 返回的代码
     */
    private String responseCode;
    
    /**
     * 返回的消息
     */
    private String responseMsg;
}
