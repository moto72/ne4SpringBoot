package com.tmsps.ne4springboot.orm;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmsps.ne4springboot.orm.model.DataModel;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangwei 396033084@qq.com
 */
@Slf4j
public class ORMUtil {

	public static List<String> getColumnLables(ResultSet rs) {
		List<String> columnLables = new ArrayList<String>();
		ResultSetMetaData metaData = null;
		try {
			metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			for (int i = 0; i < count; i++) {
				columnLables.add(metaData.getColumnLabel(i + 1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnLables;
	}

	public static <T extends DataModel> T fillMapToBean(final Class<?> clazz, Map<String, Object> map) {
		if (ObjectUtil.isNull(map)) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			T obj = (T) clazz.getDeclaredConstructor().newInstance();
			List<Field> fs = ClassUtil.getClassFields(clazz);
			for (Field field : fs) {
				Object val = map.get(field.getName());
				ClassUtil.setClassVal(field, obj, val);
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T fillMapToAnyBean(final Class<T> clazz, Map<String, Object> map) {
		if (ObjectUtil.isNull(map)) {
			return null;
		}
		try {
			T obj = clazz.getDeclaredConstructor().newInstance();
			List<Field> fs = ClassUtil.getClassFields(clazz);
			for (Field field : fs) {
				Object val = map.get(field.getName());
				ClassUtil.setClassVal(field, obj, val);
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String replaceFormatSqlOrderBy(String sql) {
		sql = sql.replaceAll("(\\s)+", " ");
		int index = sql.toLowerCase().lastIndexOf("order by");
		if (index > sql.toLowerCase().lastIndexOf(")")) {
			String sql1 = sql.substring(0, index);
			String sql2 = sql.substring(index);
			sql2 = sql2.replaceAll("[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*", "");
			return sql1 + sql2;
		}
		return sql;
	}

	/**
	 * @author zhangwei 396033084@qq.com
	 */
	public static Map<String, Object> convertResultSetToMap(ResultSet rs) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultSetMetaData rm = rs.getMetaData();
			int columnCount = rm.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				map.put(rm.getColumnLabel(i), rs.getObject(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return map;
	}

	public static <T> T fillResultSetToBean(final ResultSet rs, final Class<T> clazz) {
		if (ObjectUtil.isNotNull(rs)) {
			return fillMapToAnyBean(clazz, convertResultSetToMap(rs));
		}
		return null;
	}

	public static <T> List<T> fillMapToList(final List<Map<String, Object>> list, final Class<T> clazz) {
		if (ObjectUtil.isNull(list)) {
			return null;
		}else{
			List<T> targetList = new ArrayList<T>();
			for (Map<String, Object> map : list) {
				targetList.add(fillMapToAnyBean(clazz, map));
			}
			return targetList;
		}
	}

}
