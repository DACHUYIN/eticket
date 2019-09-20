package com.wechatapp.eticket.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author virgo.zx
 * @date 2019/8/24 22:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginResponseDTO {

    /**
     * token的相关信息
     */
    private JwtTokenResponseDTO token;

    /**
     * 用户信息
     */
    private UserInfoDTO userInfoDTO;
    
    /**
     * 返回的代码
     */
    private String responseCode;
    
    /**
     * 返回的消息
     */
    private String responseMsg;
}
