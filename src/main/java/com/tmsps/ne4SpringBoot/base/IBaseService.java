package com.tmsps.ne4SpringBoot.base;

import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tmsps.ne4SpringBoot.orm.model.DataModel;
import com.tmsps.ne4SpringBoot.page.Page;

/**
 * 
 *======================================================
 * @author zhangwei 396033084@qq.com 
 *------------------------------------------------------
 * IBaseService base servier interface
 *======================================================
 */
public interface IBaseService {
	public Logger log = LoggerFactory.getLogger("Ne4Spring");
	
	public JdbcTemplate getJdbcTemplate();
	
	public int saveObj(DataModel model);
	public int saveObj(DataModel model, boolean sync);

	public int save(String tableName, Record record);
	public void saveObjs(List<DataModel> objs);

	@Deprecated
	public <T extends DataModel> T findObjById(Object idVal, final Class<? extends DataModel> clazz);
	public <T extends DataModel> T findById(Object idVal, final Class<? extends DataModel> clazz) ;
	public Map<String, Object> findById(String tableName, String pkName, String pkVal);
	public Map<String, Object> findById(String tableName, String pkVal);
	
	public List<Map<String, Object>> findList(String sql, Object[] vals);
	public Map<String, Object> findObj(String sql, Object[] vals);
	public Map<String, Object> findObj(String sql);
	public <T extends DataModel> T findObj(String sql, Class<? extends DataModel> clazz);
	public <T extends DataModel> T findForObj(String sql, Class<? extends DataModel> modelClass);
	public List<Map<String, Object>> findList(String sql) ;
	
	public <T extends DataModel> T  findObj(String sql, Object[] vals, Class<? extends DataModel> modelClass) ;

	public int deleteObjById(String id, Class<? extends DataModel> clazz);
	public int deleteByID(String pkColumn, String id, String tableName);

	public int updateObj(DataModel obj) ;
	public int updateChangeObj(DataModel obj);
	public int updateTemplateObj(DataModel obj);
	
	public List<Map<String, Object>> findList(String sql, String sql_cnt, Object[] vals, Page page) ;
	public List<Map<String, Object>> findList(String sql, Object[] vals, Page page);
	public List<Map<String, Object>> findList(String sql, Page page);
	
	
	public int updObj(final Class<?> clazz, Map<String, Object> parm, Map<String, Object> whereparm) ;

	public Map<String, Object> getMap(String key, Object val);
	public Map<String, List<Object>> getSearchMap();
	public Map<String, Object> getMap(String[] key, Object[] val) ;

	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras);
	public Page pageinate(int pageNumber, int pageSize, String select, String sqlExceptSelect) ;
}
