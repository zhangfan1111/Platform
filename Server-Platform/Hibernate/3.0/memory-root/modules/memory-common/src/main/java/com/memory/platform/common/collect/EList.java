package com.memory.platform.common.collect;

import java.util.ArrayList;
import java.util.List;

public class EList {
	
	public static <E> ArrayList<E> newArrayList() {
	    return new ArrayList<E>();
	  }
	
	public static <E,T> List<T> filterList(List<E> source,ListFilter<E,T> listFilter) {
		return listFilter.filter(source);
	}
}
