package com.tmsps.ne4springboot.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import cn.hutool.core.util.ObjectUtil;

/**
 *	@ClassName: BeanCopyUtil
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/03/06
 *	@Copyright: 行歌信息	
 */
public class BeanCopyUtil {

	/**
	 * 	@Description: 将非空字段进行copy
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/03/06
	 */
	public static <T> T copyNonNullProperties(T source, T target) {
		if (ObjectUtil.isAllNotEmpty(source, target)) {
			BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
			return target;
		} else {
			return null;
		}
	}
	
	/**
	 * 	@Description: 将Bean中为Null的字段属性查询出来
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/03/06
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrapper = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = wrapper.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}


}
