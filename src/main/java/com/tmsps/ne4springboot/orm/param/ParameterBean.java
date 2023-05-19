package com.tmsps.ne4springboot.orm.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 *	@ClassName: ParamBean
 *	@Description: SQL参数Bean，方便做一些封装，用来代替ne4Spring的ParamBean，做一些人性化优化。
 *	@author: zhangwei(Administrator).396033084@qq.com
 *	@date： 2022/08/24
 *	@Copyright: 行歌信息	
 */
@Data
public class ParameterBean {
	//SQL查询中涉及到的参数
	private NeParamList parametes = new NeParamList();
	//SQL语句中的select语句，从起始到from语句之前这一段
	private StringBuffer select = new StringBuffer();
	//SQL语句中from语句之后的部分。
	private StringBuffer from = new StringBuffer();
	
	/**
	 * 	@Description: SQL语句中select语句添加，一般为数据库命令的起始部分
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public void selectSQL(String selectSQL) {
		this.select.append(selectSQL.trim());
	}
	
	/**
	 * 	@Description: SQL语句的from语句部分
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public void fromSQL(String fromSQL) {
		this.from.append(StrUtil.SPACE).append(fromSQL.trim());
	}
	
	/**
	 * 	@Description: 继续追加SQL语句，正常情况下SQL语句追加在from语句之后
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public void sqlAppend(String appendSQL) {
		if(StrUtil.isNotBlank(appendSQL)) {
			this.fromSQL(appendSQL);
		}
	}
	
	/**
	 * 	@Description: SQL语句追加查询参数部分
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public void sqlParameterAppend(String appendSQL, String parameter) {
		if(StrUtil.isNotBlank(parameter)) {
			this.fromSQL(appendSQL);
			this.parametes.add(parameter);
		}
	}
	
	/**
	 * 	@Description: 多个参数语句添加，减少了代码行数，但不如单个参数语句添加更友好
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public void sqlParametersAppend(String appendSQL, String... parameters) {
		if (StrUtil.isAllNotBlank(parameters)) {
			this.fromSQL(appendSQL);
			this.parametes.add(parameters);
		}
	}
	
	public void sqlAppendLike(String appendSQL, String parameter) {
		if(StrUtil.isNotBlank(parameter)) {
			this.fromSQL(appendSQL);
			this.parametes.addLike(parameter);
		}
	}
	
	/**
	 * 	@Description: 一条语句增加多条Like
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/04/22
	 */
	public void sqlAppendMoreLike(String appendSQL, String... parameters) {
		if (StrUtil.isAllNotBlank(parameters)) {
			this.fromSQL(appendSQL);
			for (String parameter : parameters) {
				this.parametes.addLike(parameter);
			}
		}
	}
	
	/**
	 * 	@Description: 返回完整的SQL语句
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public String getSQL() {
		return select.toString().concat(from.toString());
	}
	
	/**
	 * 	@Description: 返回select语句部分
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public String getSelectSQL() {
		return select.toString();
	}  
	
	/**
	 * 	@Description: 返回from语句部分
	 *	@author: zhangwei(Administrator).396033084@qq.com
	 *	@date： 2022/08/24
	 */
	public String getFromSQL() {
		return from.toString();
	}
}
