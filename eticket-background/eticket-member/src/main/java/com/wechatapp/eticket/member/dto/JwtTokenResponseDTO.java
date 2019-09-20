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
public class JwtTokenResponseDTO {

    /**
     * token(用户登录凭证)
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expirationTime;
}
