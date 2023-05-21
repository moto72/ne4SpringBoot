package com.tmsps.ne4springboot.util;

import cn.hutool.core.util.StrUtil;

/**
 *	@ClassName: NeStringUtils
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/03/24
 *	@Copyright: 行歌信息	
 */
public class NeStringUtils {
	/**
	 * 	@Description: 两个字符串拼接
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/03/24
	 */
	public static String join(String previous, String next) {
		if (StrUtil.isAllBlank(previous, next)) {
			return "";
		}

		if (StrUtil.isBlank(previous) && StrUtil.isNotEmpty(next)) {
			return next;
		}

		if (StrUtil.isNotBlank(previous) && StrUtil.isEmpty(next)) {
			return previous;
		}
		return StrUtil.join(",", previous, next);
	}
}
