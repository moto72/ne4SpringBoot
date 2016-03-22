package com.tmsps.ne4SpringBoot.orm.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.alibaba.fastjson.JSON;
import com.tmsps.ne4SpringBoot.orm.ClassUtil;

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

	public Object getPK() {
		// 获取对象中ID属性字段
		Field idField = ClassUtil.getIdField(this.getClass());
		// 获取ID的值
		Object idVal = ClassUtil.getClassVal(idField, this);
		return idVal;
	}

	public String getTableName() {
		return ClassUtil.getClassName(getClass());
	}
}
