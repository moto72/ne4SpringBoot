package com.tmsps.ne4springboot.bean;

import java.io.Serializable;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @author zhangwei 消息交互bean
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SwapBean implements Serializable {
	private static final long serialVersionUID = -4708029980460496056L;

	private String code;
	private String msg;
	private Object data;

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
	
	/**
	 * 	@Description: 对象转成json字符串时，格式化时间;
	 * 	把非string的值转化为String类型，典型应用，解决Snowflake的ID返回前端丢失精度问题。
	 *	@author: zhangwei
	 *	@date： 2022/08/19
	 */
	public String toAllStringJson() {
		return JSON.toJSONString(this, JSONWriter.Feature.WriteNonStringValueAsString, JSONWriter.Feature.WriteNullListAsEmpty,JSONWriter.Feature.WriteMapNullValue);
	}
	
}
