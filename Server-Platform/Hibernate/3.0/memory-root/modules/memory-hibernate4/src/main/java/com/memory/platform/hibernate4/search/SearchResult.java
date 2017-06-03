package com.memory.platform.hibernate4.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchResult<T> {

	public List<T> result;
	
	public Integer totalCount;

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public Map<String, Object> getEasyUIMap() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total",totalCount);
		map.put("rows",result);
		return map;
	}
}
