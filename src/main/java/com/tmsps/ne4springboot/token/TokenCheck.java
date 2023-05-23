package com.tmsps.ne4springboot.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 避免重复提交
 *
 * 用法参考文章 https://blog.csdn.net/u014745631/article/details/104090024
 * 
 * @author 冯晓东
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {

}