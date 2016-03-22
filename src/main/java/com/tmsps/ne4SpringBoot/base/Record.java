/**
 * 
 */
package com.tmsps.ne4SpringBoot.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.tmsps.ne4SpringBoot.orm.model.DataModel;


/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class Record extends DataModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 数据库字段
	 */
	private Map<String, Object> columns;

	void setColumnsMap(Map<String, Object> columns) {
		this.columns = columns;
	}

	public Map<String, Object> getColumns() {
		if (columns == null) {
			columns = new HashMap<String, Object>();
		}
		return columns;
	}

	public Record setColumns(Map<String, Object> columns) {
		this.getColumns().putAll(columns);
		return this;
	}

	public Record remove(String column) {
		getColumns().remove(column);
		return this;
	}

	public Record clear() {
		getColumns().clear();
		return this;
	}

	public Record set(String column, Object value) {
		getColumns().put(column, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String column) {
		return (T) getColumns().get(column);
	}

	public List<String> getColumnnName() {
		if (this.columns == null) {
			return null;
		} else {
			return new ArrayList<String>(columns.keySet());
		}
	}

	public List<Object> getValues() {
		List<Object> values = new ArrayList<Object>();
		Iterator<Entry<String, Object>> it = columns.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			values.add(entry.getValue());
		}
		return values;
	}

	public String toJson() {
		return JSON.toJSONString(columns);
	}
}
