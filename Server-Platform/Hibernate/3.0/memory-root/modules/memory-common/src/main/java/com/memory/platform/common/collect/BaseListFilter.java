package com.memory.platform.common.collect;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 过滤对象中重复字段值的项
 * @author norain
 *
 * @param <I>
 */
public abstract class BaseListFilter<I> implements ListFilter<I,I>{
	Map<Object,I> map = Maps.newHashMap();
	List<I> result = EList.newArrayList();
	
	@Override
	public List<I> filter(List<I> inputList) {
		for(I input : inputList) {
			if(input != null && filterFieldValue(input) != null) {
				if(map.get(filterFieldValue(input)) == null) {
					map.put(filterFieldValue(input), input);
					result.add(input);
				}
			}
		}
		return result;
	}
	
	public abstract Object filterFieldValue(I input);
}
