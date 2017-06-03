package com.memory.platform.core.basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;

/**
 * 
 */
public class BasicVL {
	// common param
	private static String PUBLICALIAS = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String pubPath = "";
	
	private String path;

	public BasicVL() {
		
	}
	
	public BasicVL(String path) {
		this.path = path;
	}
	
	public void setParam(String propertiesPath) {
		// 获取参数
		Properties prop = new Properties();
		InputStream in = BasicVL.class.getClassLoader().getResourceAsStream(propertiesPath);
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PUBLICALIAS = prop.getProperty("PUBLICALIAS");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
		licPath = path + prop.getProperty("licPath");
		pubPath = prop.getProperty("pubPath");
	}

	public boolean verify() {
		BasicManager basicManager = LManagerHolder.getBasicManager(initLiParams());
		try {
			basicManager.install(new File(licPath));
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.getStackTrace());
			return false;
		}
		try {
			basicManager.verify();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.getStackTrace());
			return false;
		}
		return true;
	}

	private static LicenseParam initLiParams() {
		Preferences preference = Preferences.userNodeForPackage(BasicVL.class);
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

		KeyStoreParam privateStoreParam = new DefineBasicLParam(BasicVL.class, pubPath, PUBLICALIAS, STOREPWD,
				null);
		LicenseParam liParams = new DefaultLicenseParam(SUBJECT, preference, privateStoreParam, cipherParam);
		return liParams;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}