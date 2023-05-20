package com.tmsps.ne4SpringBoot.test.sql;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4SpringBoot.test.model.app_user;
import com.tmsps.ne4springboot.orm.MySQLUtil;

import cn.hutool.core.lang.Console;

/**
 *	@ClassName: MySQLTest
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/20
 *	@Copyright: 行歌信息	
 */
public class MySQLTest {
	
	@Test
	public void sql() {
		Console.error("MySQL insert: {}", MySQLUtil.getInsSQL(app_user.class));
		Console.error("MySQL update: {}", MySQLUtil.getUpdateSQL(app_user.class));
	}
}
