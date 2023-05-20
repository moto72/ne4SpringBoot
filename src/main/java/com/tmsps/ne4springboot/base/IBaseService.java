package com.tmsps.ne4springboot.base;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tmsps.ne4springboot.orm.model.DataModel;
import com.tmsps.ne4springboot.orm.param.NeParamList;
import com.tmsps.ne4springboot.orm.page.Page;

/**
 *======================================================
 * @author zhangwei 396033084@qq.com 
 *------------------------------------------------------
 * IBaseService base service interface
 *======================================================
 */
public interface IBaseService {

	//获取对象JdbcTemplate，便于使用JdbcTemplate提供的封装方法
	public JdbcTemplate getJdbcTemplate();

	//获取NamedParameterJdbcTemplate
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

	//与数据库表的信息对应,保存进入数据库,sync 为是否需要同步一次Model,用于数据库中默认属性的回填
	public int save(DataModel model);

	public DataModel save(DataModel model, boolean sync);

	public int saveTemplate(DataModel model);

	public DataModel saveTemplate(DataModel model, boolean sync);

	//批量保存数据
	public void saves(List<? extends DataModel> datas);

	public <T extends DataModel> T findObjById(Object idVal, final Class<? extends DataModel> clazz);

	public <T extends DataModel> T findById(Object idVal, final Class<? extends DataModel> clazz);

	public Map<String, Object> findObj(String sql);

	public Map<String, Object> findObj(String sql, Object[] vals);

	public <T extends DataModel> T findObj(String sql, Class<? extends DataModel> clazz);

	public List<Map<String, Object>> findList(String sql);

	public <T> List<T> findList(String sql, Class<T> clazz);

	public List<Map<String, Object>> findList(String sql, Object[] vals);

	//1.6.5新增
	public <T> List<T> findList(Class<T> clazz, String sql, Object... vals);

	//查询结果集封装成BEAN
	public <T extends DataModel> T findObj(String sql, Object[] vals, Class<? extends DataModel> modelClass);

	//1.6.5新增
	public <T> T findObj(Class<T> clazz, String sql, Object... vals);

	/**
	 * @Description: 新增查询参数为 NeParamList
	 * @author: zhangwei(Mr.z).396033084@qq.com @date： 2023/03/02
	 */
	public <T> T findObj(Class<T> clazz, String sql, NeParamList params);

	/**
	 * 修改Id类型为Object
	 */
	public int deleteObjById(Object id, Class<? extends DataModel> clazz);

	public int deleteByID(String pkColumn, Object id, String tableName);

	public int updateObj(DataModel obj);

	public int updateObjT(DataModel obj);

	public int updateChangeObj(DataModel obj);

	public int updateTemplateObj(DataModel obj);

	public List<Map<String, Object>> findList(String sql, String sql_cnt, Object[] vals, Page page);

	public List<Map<String, Object>> findList(String sql, Object[] vals, Map<String, String> sort_params, Page page);

	public List<Map<String, Object>> findList(String sql, Object[] vals, Page page);

	public List<Map<String, Object>> findList(String sql, Page page);

	//1.6.5新增
	public <T> List<T> findList(Class<T> clazz, Page page, String sql, String sql_cnt, Object... vals);

	public <T> List<T> findList(Class<T> clazz, Page page, String sql, Map<String, String> sort_params, Object... vals);

	public <T> List<T> findList(Class<T> clazz, Page page, String sql, Object... vals);

	public <T> List<T> findList(Class<T> clazz, Page page, String sql);

	public int updObj(final Class<?> clazz, Map<String, Object> parm, Map<String, Object> whereparm);

	public Map<String, Object> getMap(String key, Object val);

	public Map<String, List<Object>> getSearchMap();

	public Map<String, Object> getMap(String[] key, Object[] val);

	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras);

	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect);

	//新增page参数，少一个形参
	public Page pageinate(Page page, String select, String sqlExceptSelect, NeParamList params);

	public Page pageinate(Page page, String select, String sqlExceptSelect, Object... paras);

	public Page pageinate(Page page, String select, String sqlExceptSelect);

	//新版本的查询,节约查询参数
	public List<Map<String, Object>> findList(String sql, NeParamList params, Map<String, String> sort_params, Page page);

	public List<Map<String, Object>> findList(String sql, NeParamList params);

	//1.6.5新增
	public <T> List<T> findList(Class<T> clazz, String sql, NeParamList params, Map<String, String> sort_params, Page page);

	public <T> List<T> findList(Class<T> clazz, String sql, NeParamList params);

	//1.6.7新增
	public <T> T queryForObject(String sql, Class<T> requiredType);

	public <T> T queryForObject(String sql, NeParamList params, Class<T> requiredType);
}
