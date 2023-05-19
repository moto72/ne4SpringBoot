package com.tmsps.ne4springboot.orm.model;

import java.io.Serializable;

import com.alibaba.fastjson2.JSON;
import com.tmsps.ne4springboot.annotation.NotMap;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class DataModel implements Serializable {
	
	@NotMap
	private static final long serialVersionUID = -2624604146613754567L;

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
