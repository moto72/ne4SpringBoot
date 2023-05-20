package com.tmsps.ne4springboot.orm.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONField;
import com.tmsps.ne4springboot.annotation.NotMap;
import com.tmsps.ne4springboot.orm.ClassUtil;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class DataModel implements Serializable {
	
	@NotMap
	private static final long serialVersionUID = -2624604146613754567L;

	@JSONField(serialize = false)
	public Object getPK() {
		// 获取对象中ID属性字段
		Field idField = ClassUtil.getIdField(this.getClass());
		// 获取ID的值
		Object idVal = ClassUtil.getClassVal(idField, this);
		return idVal;
	}
	
	/**
	 * 	@Description: 根据字段名称获取字段的值.
	 * 	getDeclaredFiled 仅能获取类本身的属性成员(包括私有、共有、保护).
	 * 	getField 仅能获取类(及其父类) public属性成员.
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/22
	 */
	public Object getFieldValByName(String fieldName) {
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			Object fieldVal = ClassUtil.getClassVal(field, this);
			return fieldVal;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 	@Description: 给Model对象中的属性赋值
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/22
	 */
	public boolean setValtoField(String fieldName, Object fieldValue) {
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			ClassUtil.setClassVal(field, this, fieldValue);
			return true;
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@JSONField(serialize = false)
	public String getTableName() {
		return ClassUtil.getTableName(getClass());
	}
	
	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	public String toAllStringJson() {
		return JSON.toJSONString(this, JSONWriter.Feature.WriteNonStringValueAsString, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteMapNullValue);
	}
}
