package com.tmsps.ne4springboot.orm.param;

import java.io.Serializable;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

@Getter
public class NeParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean isNull = true;
	private Object value;

	public boolean isNotNull() {
		return !isNull;
	}

	public NeParam setValue(Object value) {
		this.isNull = ObjectUtil.isNull(value);
		this.value = value;
		return this;
	}
}
