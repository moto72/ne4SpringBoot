package com.tmsps.ne4springboot.orm.model;

import java.io.Serializable;

import com.alibaba.fastjson2.JSON;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
@SuppressWarnings("serial")
public class DataModel implements Serializable {
	
	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
