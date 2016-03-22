package com.tmsps.ne4SpringBoot.utils.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableMeta {
	public String tableName;// 表名称
	public String idName = "";// 主键名称
	public List<Map<String, String>> columInfo = new ArrayList<Map<String, String>>();
	
	//**********************************************
	public String beanContent;		//生成bean内容
	

}
