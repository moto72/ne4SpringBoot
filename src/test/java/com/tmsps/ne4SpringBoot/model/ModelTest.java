package com.tmsps.ne4SpringBoot.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4springboot.orm.ClassUtil;

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
		System.err.println(ClassUtil.getTableName(app_user.class));
	}
}
