package com.tmsps.ne4SpringBoot.test.sql;

import java.util.List;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import com.tmsps.ne4SpringBoot.test.model.app_user;
import com.tmsps.ne4springboot.orm.ClassUtil;
import com.tmsps.ne4springboot.orm.MySQLUtil;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

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
//		Console.error("MySQL getInsSQL: {}", MySQLUtil.getInsSQL(app_user.class));
//		Console.error("MySQL getUpdateSQL: {}", MySQLUtil.getUpdateSQL(app_user.class));
//		Console.error("MySQL getDelRealSQL: {}", MySQLUtil.getDelRealSQL(app_user.class));
//		Console.error("MySQL getNamedParameterInsertSQL: {}", MySQLUtil.getNamedParameterInsertSQL(app_user.class));
//		Console.error("MySQL getNamedParameterUpdateSQL: {}", MySQLUtil.getNamedParameterUpdateSQL(app_user.class));
		Console.error("MySQL generateSelectSQLPrefix: {}", MySQLUtil.generateSelectSQLPrefix(app_user.class, "t"));
	}
	
}
