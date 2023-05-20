package com.tmsps.ne4springboot.annotation;

import java.lang.annotation.*;

/**
 * 逻辑删除
 *
 * @author zhangwei 396033084@qq.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NeStatus {

}
