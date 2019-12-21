package com.wechatapp.eticket.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要保证幂等性的接口上使用此方法
 *
 * @author virgo.zx
 * @date 2019/11/30 14:24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckIdempotent {
}
