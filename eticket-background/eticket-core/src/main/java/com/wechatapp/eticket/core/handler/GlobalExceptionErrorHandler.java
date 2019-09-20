package com.wechatapp.eticket.core.handler;

import com.wechatapp.eticket.core.common.exception.LoginSecurityException;
import com.wechatapp.eticket.core.dto.ErrorBodyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉处理类
 *
 * @author virgo.zx
 * @date 2019/8/24 22:08
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {

    @ExceptionHandler(LoginSecurityException.class)
    public ResponseEntity<ErrorBodyDTO> error(LoginSecurityException e) {
        log.warn("发生LoginSecurityException异常", e);
        return new ResponseEntity<>(
                ErrorBodyDTO.builder()
                        .body("非法的Token, 该用户不允许访问！")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
