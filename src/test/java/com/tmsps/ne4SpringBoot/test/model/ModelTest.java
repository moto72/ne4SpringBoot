package com.tmsps.ne4SpringBoot.test.model;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4springboot.orm.ClassUtil;

import cn.hutool.core.lang.Console;
import cn.hutool.db.meta.Table;
import lombok.extern.slf4j.Slf4j;

/**
 *	@ClassName: ModelTest
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/19
 *	@Copyright: 行歌信息	
 */
public class ModelTest {
	app_user user = new app_user()
			.setUser_id(1234567890L)
			.setUser_name("管理员")
			.setRegistration_time(LocalDateTime.now())
			.setSort(0)
			.setCreated(System.currentTimeMillis())
			.setStatus(1);
	
	@Test
	public void classTest() {
		Console.log("app_user tableName: {}", ClassUtil.getTableName(app_user.class));
	}
	
	@Test
	public void jsonTest() {
		Console.log("app_user toJsonString: {}", user.toJsonString());
	}
	
	@Test
	public void fieldNameTest() {
		List<String> columns = ClassUtil.getPropertyName(app_user.class);
		Console.log("columns: {}", columns);
	}
	
	@Test
	public void getValues() {
		List<Object> values = ClassUtil.getValuesPar(user);
		Console.log("values: {}", values);
	}
}
