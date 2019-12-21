package com.wechatapp.eticket.transaction.aspect;

import com.wechatapp.eticket.core.common.exception.IdempotentSecurityException;
import com.wechatapp.eticket.core.common.util.JwtOperatorUtils;
import com.wechatapp.eticket.transaction.service.IIdempotentTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验请求的是否含有token，来防止重复提交
 *
 * @author virgo.zx
 * @date 2019/11/30 14:32
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckIdempotentAspect {

    private final IIdempotentTokenService iIdempotentTokenService;

    @Around("@annotation(com.wechatapp.eticket.core.annotation.CheckIdempotent)")
    public Object  CheckIdempotent(ProceedingJoinPoint point) {
        try {
            log.info("从header里面获取token");
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("idempotent-token");
            log.info("获取到的token为{}", token);
            // 校验token是否合法&是否过期；如果不合法或已过期直接抛异常；如果合法放行
            iIdempotentTokenService.checkIdempotentToken(token);
            return point.proceed();
        } catch (Throwable throwable) {
            throw new IdempotentSecurityException("IdempotentToken不合法");
        }
    }
}
