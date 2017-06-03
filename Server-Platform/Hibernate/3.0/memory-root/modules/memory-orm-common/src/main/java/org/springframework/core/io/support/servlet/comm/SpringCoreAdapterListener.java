package org.springframework.core.io.support.servlet.comm;

import org.springframework.stereotype.Component;

import com.memory.platform.core.basic.BasicCore;

@Component("springCoreAdapterListener")
public class SpringCoreAdapterListener {
	public void runBaisc() {
		BasicCore.runCore();
	}
}
