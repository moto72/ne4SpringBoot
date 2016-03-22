package com.tmsps.ne4SpringBoot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *======================================================
 * @author zhangwei 396033084@qq.com 
 *------------------------------------------------------
 * Table
 *======================================================
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
	String value() default "";

	public String TableName() default "";
}
