package com.tmsps.ne4springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *	@ClassName: Permissions
 *	@Description: 权限注解
 *	@author: zhangwei(Administrator).396033084@qq.com
 *	@date： 2022/09/02
 *	@Copyright: 行歌信息	
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {
	/**
	 * 	@Description: 权限的名称
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2022/10/08
	 */
	public String name() default "";

	/**
	 * 	@Description: 权限跟随，比如唯一性验证跟随新增方法的时候，当用户被赋予新增权限时，就可以隐性的获取唯一性校验权限了
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2022/10/08
	 */
	public String follow() default "";
	
	/**
	 * 	@Description: 验证的方式，比如 不校验"exclude"、一般校验"simple"、中度校验"normal"，深度校验"hard"。
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2022/10/08
	 */
	public String verification() default VerifyType.NORMAL;
}
