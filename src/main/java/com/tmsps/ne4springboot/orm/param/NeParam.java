package com.tmsps.ne4springboot.orm.param;

import java.io.Serializable;

import cn.hutool.core.util.StrUtil;

public class NeParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean isNull;
	private Object value;

	public boolean getIsNull() {
		return isNull;
	}

	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (StrUtil.isBlankIfStr(value)) {
			this.setIsNull(true);
		}
		this.value = value;
	}

}
