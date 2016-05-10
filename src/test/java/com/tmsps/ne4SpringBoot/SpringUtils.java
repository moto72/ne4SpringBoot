package com.tmsps.ne4SpringBoot;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import com.alibaba.fastjson.JSON;
import com.tmsps.ne4spring.utils.generator.BeanGenerator;
import com.tmsps.ne4spring.utils.generator.GeUtils;
import com.tmsps.ne4spring.utils.generator.TableMeta;

public class SpringUtils {
	public static void main(String[] args) throws SQLException {
		long start = System.currentTimeMillis();
		System.err.println("开始生成");
		DataSource ds = (DataSource) SpringService.getBean("dataSource");
		GeUtils ge = new GeUtils(ds.getConnection());
		//bean文件输出的文件夹路径
		String outPath = ge.getPath()+ "/src/test/java/com/tmsps/ne4SpringBoot/bean";
		System.err.println(outPath);
		List<TableMeta> tableMetas =  ge.getTableMetas();
		System.err.println(JSON.toJSON(tableMetas));
		for (TableMeta tableMeta2 : tableMetas) {
			//bean的package 路径
			new BeanGenerator("com.tmsps.ne4SpringBoot.bean").getBeanContent(tableMeta2);
		}
		for (TableMeta tableMeta2 : tableMetas) {
			try {
				ge.wirtToFile(tableMeta2, outPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.err.println("运行结束，耗时 ："+(end-start)/1000);
	}
}
