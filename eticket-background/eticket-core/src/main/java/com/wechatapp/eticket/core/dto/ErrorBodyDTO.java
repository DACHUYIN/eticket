package com.wechatapp.eticket.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author virgo.zx
 * @date 2019/8/24 22:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBodyDTO {

    private String body;

    private int status;
}
