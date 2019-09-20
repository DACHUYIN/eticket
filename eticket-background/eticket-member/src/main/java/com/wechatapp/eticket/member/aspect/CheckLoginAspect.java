package com.wechatapp.eticket.member.aspect;

import com.wechatapp.eticket.core.common.exception.LoginSecurityException;
import com.wechatapp.eticket.core.common.util.JwtOperatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验请求的连接里面是否含有登录凭证token，如果不带token的话无法访问连接
 * 
 * @author virgo.zx
 * @date 2019/8/24 21:56
 */
@Aspect
@Component
@Slf4j
public class CheckLoginAspect {

    @Around("@annotation(com.wechatapp.eticket.core.annotation.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) {
        try {
            log.info("从header里面获取token");
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("X-Token");
            log.info("获取到的token为{}", token);
            // 校验token是否合法&是否过期；如果不合法或已过期直接抛异常；如果合法放行
            Boolean isValid = JwtOperatorUtils.validateToken(token);
            if (!isValid) {
                throw new LoginSecurityException("Token不合法！");
            }
            return point.proceed();
        } catch (Throwable throwable) {
            throw new LoginSecurityException("Token不合法");
        }
    }
}
