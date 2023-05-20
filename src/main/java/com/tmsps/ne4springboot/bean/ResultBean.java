package com.tmsps.ne4springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *	@ClassName: ResultBean
 *	@Description: 逻辑返回Bean，针对 true 和 false 返回值的扩展
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/02/28
 *	@Copyright: 行歌信息	
 */
@Data
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean implements Serializable {
	private static final long serialVersionUID = -5135834636553108937L;

	private Boolean result = false;
	private String msg = "数据异常";
}
