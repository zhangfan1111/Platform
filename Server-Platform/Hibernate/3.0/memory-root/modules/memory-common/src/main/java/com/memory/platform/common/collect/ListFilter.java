package com.memory.platform.common.collect;

import java.util.List;


public interface ListFilter<I,O> {

	public List<O> filter(List<I> inputList);
}
