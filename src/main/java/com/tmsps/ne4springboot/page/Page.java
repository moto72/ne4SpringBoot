/**
 * 
 */
package com.tmsps.ne4springboot.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;

import lombok.Data;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
@Data
public class Page implements Serializable {
	private static final long serialVersionUID = -1936470346516905569L;
	
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private int pageNumber = 1;
	private int pageSize = 20;
	private int totalPage = 1;
	private int totalRow = 0;
	//是否第一页
	private boolean isFrist = false;
	//是否最后一页
	private boolean isLast = false;
	//是否有前一页
	private boolean isHavePrevious = false;
	//是否有后一页
	private boolean isHaveNext = false;

	public Page() {}
	
	public Page(int pageNumber,int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public Page(List<Map<String, Object>> result, int pageNumber, int pageSize,
			int totalPage, int totalRow) {
		this.list = result;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
		/**
		 *  @author zhangwei 2022/09/09
		 */
		this.isFrist = (pageNumber == 1);
		this.isLast = (pageNumber == totalPage);
		this.isHavePrevious = (pageNumber > 1);
		this.isHaveNext = (pageNumber < totalPage);
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
