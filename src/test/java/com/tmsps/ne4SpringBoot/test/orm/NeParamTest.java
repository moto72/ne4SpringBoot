package com.tmsps.ne4SpringBoot.test.orm;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4springboot.orm.param.NeParam;

import cn.hutool.core.lang.Console;

/**
 *	@ClassName: NeParamTest
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/20
 *	@Copyright: 行歌信息	
 */
public class NeParamTest {

	@Test
	public void neParam() {
		NeParam param = new NeParam().setValue("这里是赋值");
		Console.error("param:{}", param);
		Console.error("param.getValue:{}", param.getValue());
		Console.error("param.getIsNull:{}", param.isNull());
		Console.error("param.isNotNull:{}", param.isNotNull());
	}
}
