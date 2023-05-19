package com.tmsps.ne4springboot.annotation;

/**
 *	@ClassName: VerifyType
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2022/10/08
 *	@Copyright: 行歌信息
 */
public interface VerifyType {
	/**
	 * 排除校验
	 */
	String EXCLUDE = "exclude";
	
	/**
	 * 简单校验
	 */
	String SIMPLE = "simple";
	
	/**
	 * 一般校验
	 */
	String NORMAL = "normal";
	
	/**
	 * 深度校验
	 */
	String HARD = "hard";
}
