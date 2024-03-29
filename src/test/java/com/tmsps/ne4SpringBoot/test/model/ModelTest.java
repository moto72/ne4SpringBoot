package com.tmsps.ne4SpringBoot.test.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSON;
import com.tmsps.ne4springboot.annotation.UpdateIgnore;
import com.tmsps.ne4springboot.orm.ClassUtil;

import cn.hutool.core.lang.Console;

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
	
	@Test
	public void toJSON() {
		app_user user = new app_user().setUser_id(2333L);
		app_user user1 = new app_user().setUser_id(1234567890L)
				.setUser_name("管理员")
				.setRegistration_time(LocalDateTime.now())
				.setSort(0)
				.setCreated(System.currentTimeMillis())
				.setStatus(1);
		List<app_user> list = new ArrayList<>();
		list.add(user);
		list.add(user1);
		Console.log("user to Json:{}", user1.toJsonString());
		Console.log("users to Json:{}", JSON.toJSONString(list));
		Console.log("user to JsonAllString:{}", user1.toAllStringJson());
	}
	
	@Test
	public void mapJson() {
		Map<String, Object> data = new HashMap<>();
		data.put("id", 1638790012801781761L);
		Console.error("map :{}", JSON.toJSONString(data));
	}
	
	@Test
	public void collection() {
		System.err.println(Arrays.asList(new String[] {"tableName","PK"}));
	}
	
	@Test
	public void getTargetA() {
		System.err.println(ClassUtil.getTargetAnnotationPropertyName(app_user.class, UpdateIgnore.class));
	}
}
