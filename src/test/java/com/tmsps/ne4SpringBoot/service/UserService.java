package com.tmsps.ne4SpringBoot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tmsps.ne4spring.base.BaseMySQLService;



@Service
public class UserService extends BaseMySQLService{
	
	
	public String say(){
		final String sql = "SELECT * FROM wx_user t order by t.subscribe_time desc";
		List<Map<String, Object>> list = this.findList(sql);
		return JSON.toJSONString(list);
	}
}
