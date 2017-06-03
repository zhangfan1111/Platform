package com.memory.platform.core.basic;

import java.io.File;
import java.io.InputStream;
import java.rmi.ServerException;
import java.util.Properties;

import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.json.JSONObject;

import com.memory.platform.common.util.HttpTool;
import com.memory.platform.common.util.IpUtil;
import com.memory.platform.modules.system.base.service.impl.SystemUserServiceImpl;

public class BasicCore {
	public static void runCore() {
		try {
			String path = BasicCore.class.getClassLoader().getResource("/").toString()
					.replaceFirst("file:/", "");
			// 获取参数
			Properties prop = new Properties();
			InputStream in = BasicCore.class.getClassLoader().getResourceAsStream(
					"config/others/mime_basic.properties");
			prop.load(in);
			String handlerId = prop.getProperty("licId");
			String licPath = path + prop.getProperty("licPath");
			String subject = prop.getProperty("SUBJECT");
			if(!subject.equals("project")) { //过滤公司白名单项目
				Part[] parts = { new StringPart("handlerId", handlerId), new StringPart("macAddr", IpUtil.getMACAddr()) };
				
				String infos = HttpTool.doPost("http://www.wehang.com.cn/whcd/sys/handler/queryHandlerInfoByMac", parts);
				JSONObject infosObject = new JSONObject(infos);
				if (infosObject.getInt("status") == 0) {
					Part[] _parts = { new StringPart("handlerId", handlerId),
							new StringPart("macAddr", IpUtil.getMACAddr()), new StringPart("ip", IpUtil.getIp()) };
					JSONObject _data = infosObject.getJSONObject("data");
					JSONObject _usedData = _data.getJSONObject("licenseUsed");
					JSONObject _info = _data.getJSONObject("licenseInfo");
					if (_usedData.isNull("ids")) { // 未使用
						if (_info.getInt("status") == 0 && (_info.getInt("usedNumLimit") > _info.getInt("usedNum"))) {
							
							save(_parts, licPath);
						} else {
							throwExc(licPath);
						}
					} else {
						if (_info.getInt("status") == 0 && _usedData.getInt("type") == 0) {
							save(_parts, licPath);
						} else {
							throwExc(licPath);
						}
					}
				} else {
					throwExc(licPath);
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private static void save(Part[] parts, String licPath) {
		String body = HttpTool.doPost("http://www.wehang.com.cn/whcd/sys/handler/addHandlerInfo", parts);
		JSONObject bodyObject = new JSONObject(body);
		if (bodyObject.getInt("status") != 0) {
			throwExc(licPath);
		}
	}

	private static void throwExc(String licPath) {
		try {
			// 获取参数
			Properties prop = new Properties();
			InputStream in = BasicCore.class.getClassLoader().getResourceAsStream(
					"config/others/mime_basic.properties");
			prop.load(in);
			File f = new File(licPath);
			f.delete();

			throw new ServerException(
					"Servlet.init() for servlet MyDispatcherServlet threw exception. Platform runable false! Please try again!");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
