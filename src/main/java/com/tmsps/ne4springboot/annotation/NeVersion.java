package com.tmsps.ne4springboot.annotation;

import java.lang.annotation.*;

/**
 * 乐观锁注解
 *
 * @author zhangwei 396033084@qq.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NeVersion {

}
