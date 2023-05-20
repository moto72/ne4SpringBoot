package com.tmsps.ne4SpringBoot.test.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.tmsps.ne4springboot.annotation.NotMap;
import com.tmsps.ne4springboot.annotation.PK;
import com.tmsps.ne4springboot.annotation.Table;
import com.tmsps.ne4springboot.orm.model.DataModel;
import com.tmsps.ne4springboot.serializer.FastJson2StringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *	@ClassName: app_user
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/19
 *	@Copyright: 行歌信息	
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(TableName = "app_user")
public class app_user extends DataModel {
	@NotMap
	private static final long serialVersionUID = -2266832993448971804L;
	@PK
	@JSONField(serializeUsing = FastJson2StringSerializer.class)
	private Long user_id;
	private String user_name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registration_time;
	
	private Integer sort;
	private Long created;
	private Integer status;
}
