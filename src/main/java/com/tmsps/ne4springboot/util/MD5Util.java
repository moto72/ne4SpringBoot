package com.tmsps.ne4springboot.util;

import org.springframework.util.DigestUtils;

/**
 * MD加密工具
 * 
 * @author zhangwei
 * 
 */
public class MD5Util {
	private static final String MD5_SALT = "NE_CORE";

	/**
	 *======================================================
	 * @author zhangwei 396033084@qq.com 
	 *------------------------------------------------------
	 * add salt
	 *======================================================
	 */
	public static String MD5(final String str, final String salt) {
		return DigestUtils.md5DigestAsHex(str.concat(salt).getBytes());
	}

	public static String MD5(final String str) {
		return MD5(str, MD5_SALT);
	}
	
	/**
	 *  Normal md5 
	*/
	public static String MD5Normal(final String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes());
	}
}
