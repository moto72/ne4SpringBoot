package com.tmsps.ne4springboot.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

/**
 *	@ClassName: JsonUtil
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/20
 *	@Copyright: 行歌信息	
 */
public class JsonUtil {
	
	public static String toJsonString(Object object) {
		return JSON.toJSONString(object);
	}
	
	/**
	 * 	@Description: 对象转成json字符串时，格式化时间;
	 * 	把非string的值转化为String类型，典型应用，解决Snowflake的ID返回前端丢失精度问题。
	 *	@author: zhangwei
	 *	@date： 2022/08/19
	 */
	public static String toAllStringJson(Object object) {
		return JSON.toJSONString(object, JSONWriter.Feature.WriteNonStringValueAsString, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteMapNullValue);
	}
}
