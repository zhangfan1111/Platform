package com.memory.platform.core.basic;


public class DefineBasicLParam extends DefineAbstractKeyStoreParam {
	private final String alias, storePwd, keyPwd;

	public DefineBasicLParam(final Class clazz, final String resource, final String alias, final String storePwd,
			final String keyPwd) {
		super(clazz, resource);
		this.alias = alias;
		this.storePwd = storePwd;
		this.keyPwd = keyPwd;
	}

	public String getAlias() {
		return alias;
	}

	public String getStorePwd() {
		return storePwd;
	}

	public String getKeyPwd() {
		return keyPwd;
	}
}
