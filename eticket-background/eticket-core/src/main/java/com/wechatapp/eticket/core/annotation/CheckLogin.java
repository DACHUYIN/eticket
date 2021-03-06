package com.wechatapp.eticket.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要验证是否登录的接口上加上此注解
 *
 * @author virgo.zx
 * @date 2019/8/24 21:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
}
