package com.memory.platform.core.basic;

import de.schlichtherle.license.LicenseParam;

public class LManagerHolder {
	private static BasicManager basicManager;

	public static synchronized BasicManager getBasicManager(LicenseParam l) {
		if (basicManager == null) {
			basicManager = new BasicManager(l);
		}
		return basicManager;
	}
}
