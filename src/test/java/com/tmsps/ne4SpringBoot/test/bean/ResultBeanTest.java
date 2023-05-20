package com.tmsps.ne4SpringBoot.test.bean;

import org.junit.jupiter.api.Test;

import com.tmsps.ne4springboot.bean.ResultBean;
import com.tmsps.ne4springboot.bean.SwapBean;

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
		SwapBean swap = new SwapBean();
//		swap.code("x200");
//		swap.msg("Every things is OK");
		swap.setCode("x200");
		swap.setMsg("Every things is OK");
		Console.error(swap.toJsonString());
		Console.error(swap.toString());
		Console.error(swap.hashCode());
//		Console.error(SwapBean.x4200().hashCode());
	}
}
