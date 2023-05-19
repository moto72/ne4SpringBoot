package com.tmsps.ne4springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title:  Column.java
 * @Package com.tmsps.ne4springboot.annotation
 * @Description: 数据库字段注解
 * @author: zhangwei
 * @date:   2023年1月18日
 * @time：	上午10:43:04
 * @version  v1.0
 * @Copyright: 2019 XingGe All rights reserved.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Column {
	/**
	 *	@Description:配置映射的列名
	 *	@author: Mr72
	 *	@date:	2023-01-18 10:44:45
	 *	@return
	 */
	public String name() default "";
	/**
	 *	@Description:insertable 对提供的insert方法有效，如果设置false 就不会出现在SQL中。
	 *	@author: Mr72
	 *	@date:	2023-01-18 10:45:09
	 *	@return
	 */
	public boolean insertable() default true;
	/**
	 *	@Description:updateable对提供的update方法有效，设置为false后不会出现在SQL中。	
	 *	@author: Mr72
	 *	@date:	2023-01-18 10:45:35
	 *	@return
	 */
	public boolean updateable() default true;
}
