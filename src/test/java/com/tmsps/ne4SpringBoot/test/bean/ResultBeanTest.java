package com.tmsps.ne4SpringBoot.test.bean;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4springboot.bean.ResultBean;
import com.tmsps.ne4springboot.bean.SwapBean;
import com.tmsps.ne4springboot.util.JsonUtil;

import cn.hutool.core.lang.Console;

/**
 *	@ClassName: ResultBeanTest
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/20
 *	@Copyright: 行歌信息	
 */
public class ResultBeanTest {
	@Test
	public void resultBean() {
		SwapBean swap = new SwapBean().setCode("x200").setMsg("操作完成");
		Console.error(JsonUtil.toJsonString(swap));
		Console.error(JsonUtil.toAllStringJson(swap));
		Console.error(swap.toString());
		Console.error(swap.toJsonString());
		//Console.error(SwapBean.x4200().hashCode());

		ResultBean rsult = new ResultBean().setResult(true).setMsg("OK");
		System.err.println(JsonUtil.toJsonString(rsult));
		System.err.println(JsonUtil.toAllStringJson(rsult));
	}
}
