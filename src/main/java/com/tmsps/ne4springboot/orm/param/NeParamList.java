package com.tmsps.ne4springboot.orm.param;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import cn.hutool.core.util.StrUtil;

public class NeParamList {

	private List<NeParam> paramList = new ArrayList<NeParam>();
	// 最终值列表
	private List<Object> paramValueList = new ArrayList<Object>();

	public NeParamList add(Object value) {
		NeParam param = new NeParam();
		param.setValue(value);
		paramList.add(param);

		// 非空,加入[值] list
		if (param.isNotNull()) {
			paramValueList.add(value);
		}

		return this;
	}
	
	/**
	 * 	@Description: 多参数加入
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/02/23
	 */
	public NeParamList add(Object[] values) {
		if (!ObjectUtils.isEmpty(values)) {
			NeParam param = new NeParam();
			for (Object value : values) {
				param.setValue(value);
				paramList.add(param);
				// 非空,加入[值] list
				if (param.isNotNull()) {
					paramValueList.add(value);
				}
			}
		}
		return this;
	}

	public NeParamList addLikeL(String paramValue) {
		if (StrUtil.isNotEmpty(paramValue)) {
			paramValue = "%" + paramValue;
		}
		return add(paramValue);
	}

	public NeParamList addLikeR(String paramValue) {
		if (StrUtil.isNotEmpty(paramValue)) {
			paramValue = paramValue + "%";
		}
		return add(paramValue);
	}

	public NeParamList addLike(String paramValue) {
		if (StrUtil.isNotEmpty(paramValue)) {
			paramValue = "%" + paramValue + "%";
		}
		return add(paramValue);
	}

	// 获取最终值的参数
	public Object[] getParamValues() {
		return paramValueList.toArray();
	}// #getParamValues

	public List<NeParam> getParamList() {
		return paramList;
	}

	public static NeParamList makeParams() {
		NeParamList params = new NeParamList();
		return params;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (Object value : paramValueList) {
			sb.append(value).append(StrUtil.SPACE);
		}
		sb.append("]");
		return sb.toString();
	}
}
