package com.tmsps.ne4SpringBoot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;



@Service
public class UserService extends BaseService{
	
	
	public String say(){
		final String sql = "SELECT * FROM wx_user t order by t.subscribe_time desc";
		List<Map<String, Object>> list = baseMySQLService.findList(sql);
		return JSON.toJSONString(list);
	}
}
