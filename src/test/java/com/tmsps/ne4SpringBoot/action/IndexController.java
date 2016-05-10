package com.tmsps.ne4SpringBoot.action;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tmsps.ne4SpringBoot.SpringService;
import com.tmsps.ne4SpringBoot.service.UserService;
import com.tmsps.ne4spring.utils.generator.BeanGenerator;
import com.tmsps.ne4spring.utils.generator.GeUtils;
import com.tmsps.ne4spring.utils.generator.TableMeta;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class IndexController {
	@Autowired
	private UserService userService;

	@RequestMapping("index")
	public String index() {
		
		return userService.say();
	}
	
	@RequestMapping("go")
	public String go() throws SQLException{
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
		return "运行结束，耗时 ："+(end-start)/1000;
	}
	
}
