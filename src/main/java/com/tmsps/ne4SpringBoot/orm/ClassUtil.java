package com.tmsps.ne4SpringBoot.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tmsps.ne4SpringBoot.annotation.NotMap;
import com.tmsps.ne4SpringBoot.annotation.PK;
import com.tmsps.ne4SpringBoot.annotation.Table;


/**
 *======================================================
 * @author zhangwei 396033084@qq.com 
 *------------------------------------------------------
 * ClassUtil
 *======================================================
 */
public class ClassUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getClassName(Class clazz) {
		if (!clazz.isAnnotationPresent(Table.class)) {
			return clazz.getSimpleName().toString();
		} else if (!"".equals(((Table) clazz.getAnnotation(Table.class)).value().toString())) {
			return ((Table) clazz.getAnnotation(Table.class)).value().toString();
		} else if ("".equals(((Table) clazz.getAnnotation(Table.class)).TableName().toString())) {
			return clazz.getSimpleName().toString();
		} else {
			return ((Table) clazz.getAnnotation(Table.class)).TableName().toString();
		}
	}

	public static List<String> getPropertyName(Class<?> clazz) {
		List<String> list = new ArrayList<String>();
		List<Field> fields = getClassFields(clazz);
		for (Field field : fields) {
			if (field.isAnnotationPresent((Class<? extends Annotation>) NotMap.class)) {
				continue;
			}
			list.add(field.getName());
		}
		return list;
	}

	public static List<Field> getClassFields(Class<?> clazz) {
		List<Field> clazzField = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			clazzField.add(field);
		} // #for
		return clazzField;
	}

	public static List<Object> getValuesPar(Object obj) {
		List<Object> list = new ArrayList<Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}

			boolean acc = field.isAccessible();
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				list.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(acc);
			}
		} // for end
		return list;
	}

	/**
	 * 获取Modelbean 属性名称和属性值MAP author zhangwei 2015年10月12日 下午5:26:10
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<Object, Object> getClassKeyVal(final Object obj) {
		Map<Object, Object> classMap = new HashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			boolean acc = field.isAccessible();
			field.setAccessible(true);
			try {// name and value
				classMap.put(field.getName(), field.get(obj));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(acc);
			}
		} // for end
		return classMap;
	}

	/**
	 * 出去bean中为null的属性 author zhangwei 2015年10月12日 下午7:12:00
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<Object, Object> getClassKeyValNotNull(final Object obj) {
		Map<Object, Object> classMap = getClassKeyVal(obj);
		Iterator<Entry<Object, Object>> entries = classMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Object, Object> entry = entries.next();
			if (entry.getValue() == null) {
				entries.remove();
			}
		}
		return classMap;
	}

	public static Field getIdField(Class<?> clazz) {
		Field id = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent((Class<? extends Annotation>) PK.class)) {
				id = field;
				break;
			}
		} // #for

		if (id == null) {// 如果没有PK映射，则返回第一个字段为主键,该字段不能为NotMap注解
			for (Field field : fields) {
				if (!field.isAnnotationPresent(NotMap.class)) {
					id = field;
					break;
				}
			} // #for
		}
		return id;
	}// #getIdField

	public static Object getClassVal(Field field, Object obj) {
		boolean acc = field.isAccessible();
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			field.setAccessible(acc);
		}
	}

	public static void setClassVal(Field field, Object obj, Object val) {
		boolean acc = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(obj, val);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			field.setAccessible(acc);
		}
	}

}
