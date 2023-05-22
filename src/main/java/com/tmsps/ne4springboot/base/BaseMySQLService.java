package com.tmsps.ne4springboot.base;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmsps.ne4springboot.exception.NEServiceException;
import com.tmsps.ne4springboot.orm.ClassUtil;
import com.tmsps.ne4springboot.orm.MySQLUtil;
import com.tmsps.ne4springboot.orm.ORMUtil;
import com.tmsps.ne4springboot.orm.model.DataModel;
import com.tmsps.ne4springboot.orm.page.Page;
import com.tmsps.ne4springboot.orm.param.NeParamList;
import com.tmsps.ne4springboot.orm.param.NeParamTools;
import com.tmsps.ne4springboot.util.ChkUtil;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *	@ClassName: BaseMySQLService
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/22
 *	@Copyright: 行歌信息	
 */
@Slf4j
@Service
public class BaseMySQLService implements IBaseService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return this.namedParameterJdbcTemplate;
	}

	@Override
	public int save(DataModel model) {
		log.debug("save the model-: {}", model.toJsonString());
		if (ChkUtil.isNull(model)) {
			log.error("保存的对象不能为空");
			throw new NEServiceException("保存的对象不能为空");
		}
		// 获取对象中ID属性字段
		Field idField = ClassUtil.getIdField(model.getClass());
		// 获取ID的值
		Object idVal = ClassUtil.getClassVal(idField, model);
		// 如果ID字段为空，则生成一个主键
		if (ChkUtil.isNull(idVal)) {
			if (idField.getType() == String.class) {
				idVal = IdUtil.getSnowflakeNextIdStr();
				ClassUtil.setClassVal(idField, model, idVal);
			} else if (idField.getType() == long.class || idField.getType() == Long.class) {
				idVal = IdUtil.getSnowflakeNextId();
				ClassUtil.setClassVal(idField, model, idVal);
			}
		}
		// 获取Bean中的属性值
		final List<Object> vals = ClassUtil.getValuesPar(model);
		// 根据Bean生成插入语句
		String sql = MySQLUtil.getInsSQL(model.getClass());
		log.debug("SQL-:{}, {}", sql, vals.toArray());
		return jdbcTemplate.update(sql, vals.toArray());
	}

	@Override
	public DataModel save(DataModel model, boolean sync) {
		this.save(model);
		if (sync) {
			model = findById(model.getPK(), model.getClass());
		}
		return model;
	}

	@Override
	public int saveTemplate(DataModel model) {
		log.debug("save the model by template:{}", model.toJsonString());
		if (ChkUtil.isNull(model)) {
			log.error("保存的对象不能为空");
			throw new NEServiceException("保存的对象不能为空");
		}
		// 获取对象中ID属性字段
		Field idField = ClassUtil.getIdField(model.getClass());
		// 获取ID的值
		Object idVal = ClassUtil.getClassVal(idField, model);
		// 如果ID字段为空，则生成一个主键
		if (ChkUtil.isNull(idVal)) {
			if (idField.getType() == String.class) {
				idVal = IdUtil.getSnowflakeNextIdStr();
				ClassUtil.setClassVal(idField, model, idVal);
			} else if (idField.getType() == long.class || idField.getType() == Long.class) {
				idVal = IdUtil.getSnowflakeNextId();
				ClassUtil.setClassVal(idField, model, idVal);
			}
		}

		LinkedHashMap<Object, Object> modelKeyVal = ClassUtil.getClassKeyValNotNull(model);
		// 获取属性名称
		final List<Object> propertys = ClassUtil.getKeyList(modelKeyVal);
		// 获取属性值
		final List<Object> vals = ClassUtil.getValList(modelKeyVal);
		// 根据Bean生成插入语句
		String sql = MySQLUtil.getTemplateInsSQL(ClassUtil.getTableName(model.getClass()), propertys);
		log.debug("SQL-:{}, {}", sql, vals.toArray());
		return jdbcTemplate.update(sql, vals.toArray());
	}

	@Override
	public DataModel saveTemplate(DataModel model, boolean sync) {
		this.saveTemplate(model);
		if (sync) {
			model = this.findById(model.getPK(), model.getClass());
		}
		return model;
	}

	@Override
	@Transactional
	public void saves(List<? extends DataModel> datas) {
		if (ChkUtil.listIsNull(datas)) {
			log.error("保存的对象集合不能为空");
			throw new NEServiceException("保存的对象集合不能为空");
		}
		datas.forEach(model -> {
			this.save(model);
		});
	}

	@Override
	public <T extends DataModel> T findById(Object idVal, Class<? extends DataModel> clazz) {
		// 主键为空，无法查询，返回null
		if (ChkUtil.isNull(idVal)) {
			log.error("主键为null,无法查询");
			return null;
		}
		String sql = MySQLUtil.getSelectSQL(clazz, false);
		log.debug("SQL-:{}, {}", sql, idVal);
		return jdbcTemplate.query(sql, new ResultSetExtractor<T>() {
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					@SuppressWarnings("unchecked")
					T obj = ((T) MySQLUtil.fillPojoByResultSet(rs, clazz));
					// 只取第一个
					return (T) obj;
				}
				return null;
			}
		}, new Object[] { idVal });
	}

	@Override
	public Map<String, Object> findObj(String sql) {
		List<Map<String, Object>> list = findList(sql);
		if (ChkUtil.listIsNotNull(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> findObj(String sql, Object[] vals) {
		log.debug("SQL-:{}, {}", sql, vals);
		List<Map<String, Object>> list = this.findList(sql, vals);
		if (ChkUtil.listIsNotNull(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends DataModel> T findObj(String sql, Class<? extends DataModel> clazz) {
		List<Map<String, Object>> list = findList(sql);
		if (ChkUtil.listIsNotNull(list)) {
			return (T) ORMUtil.fillMapToBean(clazz, list.get(0));
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> findList(String sql) {
		log.debug("SQL-:{}", sql);
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public <T> List<T> findList(String sql, Class<T> clazz) {
		log.debug("SQL-:{}", sql);
		List<T> list = new ArrayList<T>();
		jdbcTemplate.query(sql, new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				T t = ORMUtil.fillResultSetToBean(rs, clazz);
				list.add(t);
				return t;
			}
		});
		return list;
	}

	@Override
	public List<Map<String, Object>> findList(String sql, Object[] vals) {
		log.debug("SQL-:{}, {}", sql, vals);
		return jdbcTemplate.queryForList(sql, vals);
	}

	@Override
	public <T> List<T> findList(Class<T> clazz, String sql, Object... vals) {
		List<Map<String, Object>> list = this.findList(sql, vals);
		return ORMUtil.fillMapToList(list, clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends DataModel> T findObj(String sql, Object[] vals, Class<? extends DataModel> modelClass) {
		Map<String, Object> map = this.findObj(sql, vals);
		return (T) ORMUtil.fillMapToAnyBean(modelClass, map);
	}

	@Override
	public <T> T findObj(Class<T> clazz, String sql, Object... vals) {
		Map<String, Object> map = this.findObj(sql, vals);
		return ORMUtil.fillMapToAnyBean(clazz, map);
	}

	@Override
	public <T> T findObj(Class<T> clazz, String sql, NeParamList params) {
		return this.findObj(clazz, sql, params.getParamValues());
	}

	@Override
	public int deleteById(Object id, Class<? extends DataModel> clazz) {
		// 主键为空，无法查询，返回null
		if (ChkUtil.isNull(id)) {
			log.error("主键为null,无法删除");
			return -1;
		}
		String sql = MySQLUtil.getDelRealSQL(clazz);
		log.debug("SQL-:{}, {}", sql, id);
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int update(DataModel obj) {
		if (ChkUtil.isNull(obj)) {
			log.error("update faile , the data can not null !");
			return 0;
		} else {
			String sql = MySQLUtil.getUpdateSQL(obj.getClass());
			final LinkedHashMap<Object, Object> clsky = ClassUtil.getClassKeyValWithoutPk(obj);
			List<Object> vals = new ArrayList<Object>();
			clsky.entrySet().forEach(entry -> {
				vals.add(entry.getValue());
			});
			Object pkVal = ClassUtil.getClassVal(ClassUtil.getIdField(obj.getClass()), obj);
			vals.add(pkVal);
			log.debug("sql-:{}, {}", sql, vals);
			return jdbcTemplate.update(sql, vals.toArray());
		}
	}

	@Override
	public int updateTemplate(DataModel obj) {
		if (ChkUtil.isNull(obj)) {
			log.error("修改失败！对象不允许为空！");
			return -1;
		} else {
			String sql = MySQLUtil.getChangeUpdateSQL(obj);
			final LinkedHashMap<Object, Object> clsky = ClassUtil.getClassKeyValNotNull(obj);
			List<Object> vals = new ArrayList<Object>();
			clsky.entrySet().forEach(entry -> {
				vals.add(entry.getValue());
			});

			String pk = ClassUtil.getIdField(obj.getClass()).getName();
			Object pkVal = clsky.get(pk);
			vals.remove(pkVal);
			vals.add(pkVal);
			log.debug("sql-:{} with {}", sql, vals);
			return jdbcTemplate.update(sql, vals.toArray());
		}
	}

	@Override
	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
		if (pageNumber < 1 || pageSize < 1) {
			log.error("当前页面页数和页面记录展示条数不能小于1");
			return new Page(pageNumber, pageSize);
		}
		long totalRow = 0;
		int totalPage = 0;
		if (ObjectUtil.isAllEmpty(paras)) {
			return pageinate(pageNumber, pageSize, select, sqlExceptSelect);
		}
		totalRow = jdbcTemplate.queryForObject(String.join(StrUtil.SPACE, "SELECT COUNT(*)", sqlExceptSelect), Long.class, paras);
		if (totalRow % pageSize == 0) {
			totalPage = (int) (totalRow / pageSize);
		} else {
			totalPage = (int) (totalRow / pageSize) + 1;
		}
		log.debug("The TotalPage is : {}", totalPage);
		String sql = MySQLUtil.getPageSQL(select, sqlExceptSelect);
		// 判断当前页面是否大于最大页面
		pageNumber = pageNumber >= totalPage ? totalPage : pageNumber;
		List<Object> objs = new ArrayList<Object>(Arrays.asList(paras));
		objs.add((pageNumber - 1) * pageSize < 0 ? 0 : (pageNumber - 1) * pageSize);
		objs.add(pageSize);
		log.debug("SQL-:{} with {}", sql, objs);
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, objs.toArray());
		return new Page(result, pageNumber, pageSize, totalPage, ((Number) totalRow).intValue());
	}

	@Override
	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
		if (pageNumber < 1 || pageSize < 1) {
			log.error("当前页面页数和页面记录展示条数不能小于1");
			return new Page(pageNumber, pageSize);
		}
		long totalRow = 0;
		int totalPage = 0;
		totalRow = jdbcTemplate.queryForObject("SELECT COUNT(*) " + sqlExceptSelect, Long.class);
		if (totalRow % pageSize == 0) {
			totalPage = (int) (totalRow / pageSize);
		} else {
			totalPage = (int) (totalRow / pageSize) + 1;
		}
		log.debug("The TotalPage is : {}", totalPage);
		String sql = MySQLUtil.getPageSQL(select, sqlExceptSelect);

		// 判断当前页面是否大于最大页面
		pageNumber = pageNumber >= totalPage ? totalPage : pageNumber;
		log.debug("SQL-:{}", sql);
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, (pageNumber - 1) * pageSize < 0 ? 0 : (pageNumber - 1) * pageSize, pageSize);
		return new Page(result, pageNumber, pageSize, totalPage, ((Number) totalRow).intValue());
	}

	@Override
	public Page pageinate(Page page, String select, String sqlExceptSelect, NeParamList params) {
		return pageinate(page, select, NeParamTools.handleSql(sqlExceptSelect.toString(), params), params.getParamValues());
	}

	@Override
	public Page pageinate(Page page, String select, String sqlExceptSelect, Object... paras) {
		return pageinate(page.getPageNumber(), page.getPageSize(), select, sqlExceptSelect, paras);
	}

	@Override
	public Page pageinate(Page page, String select, String sqlExceptSelect) {
		return pageinate(page.getPageNumber(), page.getPageSize(), select, sqlExceptSelect);
	}

	@Override
	public List<Map<String, Object>> findList(String sql, NeParamList params) {
		String endSql = NeParamTools.handleSql(sql, params);
		log.debug("{},{}", endSql, params.toString());
		return jdbcTemplate.queryForList(endSql, params.getParamValues());
	}

	@Override
	public <T> List<T> findList(Class<T> clazz, String sql, NeParamList params) {
		List<Map<String, Object>> list = this.findList(sql, params);
		return ORMUtil.fillMapToList(list, clazz);
	}

	@Override
	public <T> T queryForObject(String sql, NeParamList params, Class<T> requiredType) {
		String endSql = NeParamTools.handleSql(sql, params);
		log.debug("{},{}", endSql, params.toString());
		return getJdbcTemplate().queryForObject(endSql, requiredType, params.getParamValues());
	}
}
