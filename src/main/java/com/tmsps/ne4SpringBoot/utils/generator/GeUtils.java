package com.tmsps.ne4SpringBoot.utils.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmsps.ne4SpringBoot.utils.ChkUtil;


public class GeUtils {
	protected TypeMapping typeMapping = new TypeMapping();
	protected Connection conn;
	
	public GeUtils(Connection conn){
		this.conn = conn;
	}
	public String getPath(){
		String path = null;
		try {
			path = GeUtils.class.getResource("/").toURI().getPath();
			path = new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	// 获取数据库链接
	public Connection getConnection() {
		if(ChkUtil.isNull(conn)){
			throw new IllegalArgumentException("数据库链接为空");
		}
		return this.conn;
	}
	
	//写入文件
	public void wirtToFile(TableMeta tableMeta,String beanOutputDir) throws IOException {
		File dir = new File(beanOutputDir);
		if (!dir.exists())
			dir.mkdirs();
		
		String target = beanOutputDir + File.separator + tableMeta.tableName + ".java";
		FileWriter fw = new FileWriter(target);
		try {
			fw.write(tableMeta.beanContent);
		}
		finally {
			fw.close();
		}
	}
	
	public List<TableMeta> getTableMetas() {
		List<TableMeta> tablesMeta = new ArrayList<TableMeta>();
		Connection conn = this.conn;
		DatabaseMetaData dbMeta = null;
		ResultSet rest = null;
		ResultSetMetaData rsmd = null;
		String sql = "select * from %s where 1=2";
		try {
			dbMeta = conn.getMetaData();
			rest = dbMeta.getTables(null, null, null, new String[] { "TABLE", "VIEW" });
			TableMeta tm ;
			while (rest.next()) {
				tm = new TableMeta();
				String tableName = rest.getString("TABLE_NAME");
				tm.tableName = tableName;
				//主键
				ResultSet rs2 = dbMeta.getPrimaryKeys(null, null, tableName);
				while (rs2.next()) {
					tm.idName = rs2.getString(4);
				}
				rs2.close();
				//字段信息
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(String.format(sql, tableName));
				rsmd = rs.getMetaData();
				
				
				for (int i=1; i<=rsmd.getColumnCount(); i++) {
					Map<String,String> column = new HashMap<String,String>();
					String columnName = rsmd.getColumnName(i);
					String colClassName = rsmd.getColumnClassName(i);
					String typeStr = typeMapping.getType(colClassName);
					column.put("columnName", columnName);
					column.put("columnType",  colClassName);
					column.put("typeStr",  typeStr);
					tm.columInfo.add(column);
				}
				rs.close();
				stm.close();
				
				tablesMeta.add(tm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rest != null) {
					rest.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tablesMeta;
	}
}
