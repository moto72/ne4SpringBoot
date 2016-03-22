package com.tmsps.ne4SpringBoot.base;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

import com.tmsps.ne4SpringBoot.exception.NEServiceException;
import com.tmsps.ne4SpringBoot.orm.ClassUtil;
import com.tmsps.ne4SpringBoot.orm.MySQLUtil;
import com.tmsps.ne4SpringBoot.orm.ORMUtil;
import com.tmsps.ne4SpringBoot.orm.model.DataModel;
import com.tmsps.ne4SpringBoot.page.Page;
import com.tmsps.ne4SpringBoot.utils.ChkUtil;
import com.tmsps.ne4SpringBoot.utils.GenerateUtil;
import com.tmsps.ne4SpringBoot.utils.StrUtil;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class BaseMySQLService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected JdbcTemplate jt;

	/**
	 * 
	 * author zhangwei 2015年10月12日 下午7:47:15
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	public int saveObj(DataModel model) {
		log.debug("save the model" + model.toJsonString());
		if (model == null) {
			log.error("保存失败！保存对象不允许为空！");
			throw new NEServiceException("保存失败！保存对象不允许为空！");
		}
		// 获取对象中ID属性字段
		Field idField = ClassUtil.getIdField(model.getClass());
		// 获取ID的值
		Object idVal = ClassUtil.getClassVal(idField, model);
		// 如果ID字段为空，则生成一个主键
		if (idVal == null) {
			if (idField.getType() == String.class) {
				idVal = GenerateUtil.getBase58ID();
				ClassUtil.setClassVal(idField, model, idVal);
			}
		}
		// 获取Bean中的属性值
		final List<Object> vals = ClassUtil.getValuesPar(model);
		// 根据Bean生成插入语句
		String sql = MySQLUtil.getInsSQL(model.getClass());
		log.debug(sql);
		return jt.update(sql, vals.toArray());
	}

	/**
	 * 
	 * author zhangwei 2015年10月12日 下午7:47:44
	 * @param model
	 * @param sync
	 * @return
	 */
	public int saveObj(DataModel model, boolean sync) {
		log.debug("save the model" + model.toJsonString());
		if (sync) {
			int flag = this.saveObj(model);
			model = findObjById(model.getPK(), model.getClass());
			return flag;
		} else {
			return this.saveObj(model);
		}
	}

	/**
	 * @param tableName  表名
	 * @param record 纪录对象
	 */
	public int save(String tableName, Record record) {
		final String insSQL = MySQLUtil.getInsSQL(record, tableName);
		if (!StrUtil.notBlank(insSQL)) {
			return 0;
		} else {
			return jt.update(insSQL, record.getValues().toArray());
		}
	}

	/**
	 * @param objs
	 *            保存的对象集合
	 * @throws NEServiceException
	 *             处理异常
	 */
	@Transactional
	public void saveObjs(List<DataModel> objs) throws NEServiceException {
		for (DataModel obj : objs) {
			this.saveObj(obj);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T findObjById(Object idVal, final Class<? extends DataModel> clazz) {
		// 主键为空，无法查询，返回null
		if (idVal == null) {
			log.warn("主键为null,无法查询,返回null");
			return null;
		}
		String sql = MySQLUtil.getSelectSQL(clazz, false);
		log.debug(sql);
		return jt.query(sql, new Object[] { idVal }, new ResultSetExtractor<T>() {
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					T obj = (T) MySQLUtil.fillPojoByResultSet(rs, clazz);
					// 只取第一个
					return obj;
				}
				return null;
			}
		});
	}

	public Map<String, Object> findById(String tableName, String pkName, String pkVal) {
		List<Map<String, Object>> list = jt.queryForList(MySQLUtil.getSelectByIdSQL(tableName, pkName), pkVal);
		return list.size() == 1 ? list.get(0) : null;
	}

	public Map<String, Object> findById(String tableName, String pkVal) {
		return jt.queryForMap(MySQLUtil.getSelectByIdSQL(tableName), pkVal);
	}

	public List<Map<String, Object>> findList(String sql, Object[] vals) {
		log.debug(sql);
		return jt.queryForList(sql, vals);
	}

	public Map<String, Object> findObj(String sql, Object[] vals) {
		List<Map<String, Object>> list = this.findList(sql, vals);
		if (ChkUtil.isNotNull(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Map<String, Object> findObj(String sql) {
		List<Map<String, Object>> list = findList(sql);
		if (ChkUtil.isNotNull(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T findObj(String sql, Class<T> clazz) {
		List<Map<String, Object>> list = findList(sql);
		if (ChkUtil.isNotNull(list)) {
			return (T) ORMUtil.fillMapToBean(clazz, list.get(0));
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T findForObj(String sql, Class<?> modelClass) {
		List<Map<String, Object>> list = this.findList(sql);
		if (ChkUtil.isNotNull(list)) {
			return (T) ORMUtil.fillMapToBean(modelClass, list.get(0));
		} else {
			return null;
		}
	}

	private List<Map<String, Object>> findList(String sql) {
		log.debug(sql);
		return jt.queryForList(sql);
	}

	public DataModel findObj(String sql, Object[] vals, Class<?> modelClass) {
		return ORMUtil.fillMapToBean(modelClass, findObj(sql, vals));
	}

	public int deleteObjById(String id, @SuppressWarnings("rawtypes") Class clazz) {
		final String sql = MySQLUtil.getDelRealSQL(clazz);
		return jt.update(sql, id);
	}

	public int deleteByID(String pkColumn, String id, String tableName) {
		return jt.update(MySQLUtil.getDelRealSQL(tableName, pkColumn), id);
	}

	public int updateObj(DataModel obj) {
		if (obj == null) {
			log.error("修改失败！对象不允许为空！");
			return 0;
		} else {
			String sql = MySQLUtil.getUpdateSQL(obj.getClass());
			log.debug(sql);
			final List<?> vals = ClassUtil.getValuesPar(obj);
			return jt.update(sql, new PreparedStatementSetter() {
				public void setValues(PreparedStatement pstmt) throws SQLException {
					for (int i = 1; i < vals.size(); i++) {
						pstmt.setObject(i, vals.get(i));
					}
					// 设置 ID
					pstmt.setObject(vals.size(), vals.get(0));
				}
			});
		}
	}
	
	public int updateChangeObj(DataModel obj){
		if (obj == null) {
			log.error("修改失败！对象不允许为空！");
			return 0;
		} else {
			String sql = MySQLUtil.getChangeUpdateSQL(obj);
			Map<Object, Object> clsky = ClassUtil.getClassKeyValNotNull(obj);
			Object[] objs = new Object[clsky.size()];
			int i = 0;
			for (Map.Entry<Object,Object> entry : clsky.entrySet()) {
				objs[i++] = entry.getValue();
			}
			log.debug(sql);
			return jt.update(sql, objs);
		}
	}
	
	public List<Map<String, Object>> findList(String sql, String sql_cnt, Object[] vals, Page page) {
		List<Map<String, Object>> ret = null;
		int cnt = jt.queryForObject(sql_cnt, vals, Integer.class);
		page.setTotalRow(cnt);
		page.setTotalPage((page.getTotalRow() - 1) / page.getPageSize() + 1);
		if (page.getPageNumber() > page.getTotalPage()) {
			page.setPageNumber(page.getTotalPage());
		}
		if (page.getPageNumber() <= 0) {
			page.setPageNumber(1);
		}
		// 分页
		sql += " limit :start,:pageSize";
		sql = sql.replace(":start", page.getPageSize() * (page.getPageNumber() - 1) + "");
		sql = sql.replace(":pageSize", page.getPageSize() + "");
		log.debug(sql_cnt);
		log.debug(sql);
		ret = jt.queryForList(sql, vals);
		return ret;
	}

	public List<Map<String, Object>> findList(String sql, Object[] vals, Page page) {
		String sql_cnt = MySQLUtil.getCntSql(sql);
		return findList(sql, sql_cnt, vals, page);

	}

	public List<Map<String, Object>> findList(String sql, Page page) {
		return findList(sql, null, page);
	}

	public int updObj(final Class<?> clazz, Map<String, Object> parm, Map<String, Object> whereparm) {
		StringBuffer sb = new StringBuffer(" UPDATE ");
		sb.append(" " + this.getClassName(clazz) + " ");
		sb.append(" SET ");
		List<Object> cxparm = new ArrayList<Object>();
		int i = 0;
		for (String key : parm.keySet()) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(" " + key + " = ? ");
			i++;
			cxparm.add(parm.get(key));
		}
		sb.append(" WHERE 1=1 ");
		if (ChkUtil.isNotNull(whereparm)) {
			for (String key : whereparm.keySet()) {
				sb.append(" AND " + key + " = ?  ");
				cxparm.add(whereparm.get(key));
			}
		}
		return this.jt.update(sb.toString(), cxparm.toArray());
	}

	public String getClassName(final Class<?> clazz) {
		String clsname = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
		return clsname;
	}

	public Map<String, Object> getMap(String key, Object val) {
		return getMap(new String[] { key }, new Object[] { val });
	}

	public Map<String, List<Object>> getSearchMap() {
		Map<String, List<Object>> cxparms = new HashMap<String, List<Object>>();
		cxparms.put("cname", new ArrayList<Object>());
		cxparms.put("cwhere", new ArrayList<Object>());
		cxparms.put("cval", new ArrayList<Object>());
		return cxparms;
	}

	public Map<String, Object> getMap(String[] key, Object[] val) {
		Map<String, Object> mapobj = new HashMap<String, Object>();
		for (int i = 0; i < key.length; i++) {
			mapobj.put(key[i], val[i]);
		}
		return mapobj;
	}

	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras)
			throws Exception {
		if (pageNumber < 1 || pageSize < 1) {
			log.error("当前页面页数和页面记录展示条数不能小于1");
			throw new Exception("当前页面页数和页面记录展示条数不能小于1");
		}
		long totalRow = 0;
		int totalPage = 0;
		if (!StrUtil.notNull(paras)) {
			return pageinate(pageNumber, pageSize, select, sqlExceptSelect);
		}
		totalRow = jt.queryForObject("SELECT COUNT(1) " + sqlExceptSelect, paras, Long.class);
		if (totalRow % pageSize == 0) {
			totalPage = (int) (totalRow / pageSize);
		} else {
			totalPage = (int) (totalRow / pageSize) + 1;
		}
		log.debug("The TotalPage is :" + totalPage);
		String sql = getPageSQL(select, sqlExceptSelect);
		log.debug("SQL: " + sql);
		// 判断当前页面是否大于最大页面
		pageNumber = pageNumber >= totalPage ? totalPage : pageNumber;
		List<Object> objs = new ArrayList<Object>(Arrays.asList(paras));
		objs.add((pageNumber - 1) * pageSize < 0 ? 0 : (pageNumber - 1) * pageSize);
		objs.add(pageSize);

		List<Map<String, Object>> result = jt.queryForList(sql, objs.toArray());
		return new Page(result, pageNumber, pageSize, totalPage, ((Number) totalRow).intValue());
	}

	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect) throws Exception {
		if (pageNumber < 1 || pageSize < 1) {
			log.error("当前页面页数和页面记录展示条数不能小于1");
			throw new Exception("当前页面页数和页面记录展示条数不能小于1");
		}
		long totalRow = 0;
		int totalPage = 0;
		totalRow = jt.queryForObject("SELECT COUNT(1) " + sqlExceptSelect, Long.class);
		if (totalRow % pageSize == 0) {
			totalPage = (int) (totalRow / pageSize);
		} else {
			totalPage = (int) (totalRow / pageSize) + 1;
		}
		log.debug("The TotalPage is :" + totalPage);
		String sql = getPageSQL(select, sqlExceptSelect);
		log.debug("SQL: " + sql);

		// 判断当前页面是否大于最大页面
		pageNumber = pageNumber >= totalPage ? totalPage : pageNumber;
		List<Map<String, Object>> result = jt.queryForList(sql,
				(pageNumber - 1) * pageSize < 0 ? 0 : (pageNumber - 1) * pageSize, pageSize);
		return new Page(result, pageNumber, pageSize, totalPage, ((Number) totalRow).intValue());
	}

	public String getPageSQL(String select, String sqlExceptSelect) {
		StringBuilder sb = new StringBuilder(select);
		sb.append(" ").append(sqlExceptSelect);
		sb.append(" ").append("LIMIT ?,?");
		return sb.toString();
	}

}
