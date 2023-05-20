package com.tmsps.ne4SpringBoot.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.tmsps.ne4springboot.annotation.NotMap;
import com.tmsps.ne4springboot.annotation.Table;
import com.tmsps.ne4springboot.orm.model.DataModel;

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
public class app_user extends DataModel {
	@NotMap
	private static final long serialVersionUID = -2266832993448971804L;
	
	private Long user_id;
	private String user_name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registration_time;
	
	private Integer sort;
	private Long created;
	private Integer status;
	
}
