package com.tmsps.ne4springboot.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tmsps.ne4springboot.annotation.Column;
import com.tmsps.ne4springboot.annotation.NotMap;
import com.tmsps.ne4springboot.annotation.PK;
import com.tmsps.ne4springboot.annotation.Table;
import com.tmsps.ne4springboot.orm.model.DataModel;

import cn.hutool.core.util.ObjectUtil;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
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

	public static String getTableName(Class<? extends DataModel> clazz) {
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
			if (field.isAnnotationPresent((Class<? extends Annotation>) Column.class)) {
				list.add(field.getAnnotation(Column.class).name());
				continue;
			}
			if (field.isSynthetic()) {//如果类中字段为合成字段，剔除。
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
			if (field.isSynthetic()) {//如果类中字段为合成字段，剔除。
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
			if (field.isSynthetic()) {//如果类中字段为合成字段，剔除。
				continue;
			}

			boolean acc = field.canAccess(obj);
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
	 */
	public static LinkedHashMap<Object, Object> getClassKeyVal(final Object obj) {
		LinkedHashMap<Object, Object> classMap = new LinkedHashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			if (field.isSynthetic()) {//如果类中字段为合成字段，剔除。
				continue;
			}
			boolean acc = field.canAccess(obj);
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
	 * 获取Modelbean 属性名称和属性值MAP author zhangwei 2015年10月12日 下午5:26:10
	 */
	public static LinkedHashMap<Object, Object> getClassKeyValWithoutPk(final Object obj) {
		LinkedHashMap<Object, Object> classMap = new LinkedHashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			if (field.isAnnotationPresent(PK.class)) {
				continue;
			}
			boolean acc = field.canAccess(obj);
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
	 * @Description: 用于获取对象中属性的值，剔除PK和Insertable的对象
	 * @author: Mr72
	 * @date: 2023-01-18 11:46:18
	 * @param obj
	 * @return
	 */
	public static LinkedHashMap<Object, Object> getClassKeyValWithoutPkAndInsertable(final Object obj) {
		LinkedHashMap<Object, Object> classMap = new LinkedHashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			if (field.isAnnotationPresent(PK.class)) {
				continue;
			}
			if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).insertable()) {
				continue;
			}
			boolean acc = field.canAccess(obj);
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
	 * @Description: 用于获取对象中属性的值，剔除PK和Updateable的对象
	 * @author: Mr72
	 * @date: 2023-01-18 11:46:18
	 * @param obj
	 * @return
	 */
	public static LinkedHashMap<Object, Object> getClassKeyValWithoutPkAndUpdateable(final Object obj) {
		LinkedHashMap<Object, Object> classMap = new LinkedHashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			if (field.isAnnotationPresent(PK.class)) {
				continue;
			}
			if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).updateable()) {
				continue;
			}
			boolean acc = field.canAccess(obj);
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
	 * 除去bean中为null的属性 author zhangwei 2015年10月12日 下午7:12:00
	 */
	public static LinkedHashMap<Object, Object> getClassKeyValNotNull(final Object obj) {
		LinkedHashMap<Object, Object> classMap = getClassKeyVal(obj);
		Iterator<Entry<Object, Object>> entries = classMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Object, Object> entry = entries.next();
			if (entry.getValue() == null) {
				entries.remove();
			}
		}
		return classMap;
	}

	//DataModel里的PK和所有不为空的属性
	public static LinkedHashMap<Object, Object> getClassKeyValNotNullAndPK(final Object obj) {
		LinkedHashMap<Object, Object> classMap = new LinkedHashMap<Object, Object>();
		List<Field> fields = getClassFields(obj.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(NotMap.class)) {
				continue;
			}
			if (field.isSynthetic()) {//如果类中字段为合成字段，剔除。
				continue;
			}
			boolean acc = field.canAccess(obj);
			field.setAccessible(true);
			try {// name and value
				if (field.isAnnotationPresent(PK.class)) {//如果为PK
					classMap.put(field.getName(), field.get(obj));
				} else if (ObjectUtil.isNotNull(field.get(obj))) {
					classMap.put(field.getName(), field.get(obj));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(acc);
			}
		} // for end
		return classMap;
	}

	//将Map中的Key转换成List
	public static List<Object> getKeyList(final Map<Object, Object> map) {
		Set<Object> keys = map.keySet();
		return new ArrayList<Object>(keys);
	}

	//将Map中的values转换为List
	public static List<Object> getValList(final Map<Object, Object> map) {
		return new ArrayList<Object>(map.values());
	}

	//find Pk from Class if not have @pk return 1st
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
		boolean acc = field.canAccess(obj);
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
		boolean acc = field.canAccess(obj);
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
