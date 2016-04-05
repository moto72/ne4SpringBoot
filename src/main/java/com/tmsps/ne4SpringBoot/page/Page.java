/**
 * 
 */
package com.tmsps.ne4SpringBoot.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class Page implements Serializable {
	private static final long serialVersionUID = -1936470346516905569L;
	
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private int pageNumber = 1;
	private int pageSize = 8;
	private int totalPage = 1;
	private int totalRow = 0;

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
	}

	// =======================get & set ====================================//

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
