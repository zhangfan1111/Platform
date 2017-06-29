package com.utils.tianyi;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memory.platform.common.util.HttpInvoker;
import com.memory.platform.common.util.HttpTool;

public class SMSUtils {
	
	private static final String ACCESS_TOKEN_URL = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";
	private static final String SEND_SMS_URL = "http://api.189.cn/v2/emp/templateSms/sendSms";

	private static final String APP_ID = "316392550000250363";
	private static final String APP_SECRET = "84bb61402e3a6142c31cbe4cb37bb534";
	private static final String GRANT_TYPE = "client_credentials";
	private static final String TEMPLATE_ID = "91550513";

	private static String getAccessToken() throws IOException, JSONException {
		NameValuePair grantTypePair = new NameValuePair("grant_type",
				GRANT_TYPE);
		NameValuePair appIdPair = new NameValuePair("app_id", APP_ID);
		NameValuePair appSecretPair = new NameValuePair("app_secret",
				APP_SECRET);
		String body = HttpTool.doPosts(ACCESS_TOKEN_URL, new NameValuePair[] {
				grantTypePair, appIdPair, appSecretPair });
		if (body != null) {
			GetTokenResult result = new Gson().fromJson(body,
					GetTokenResult.class);
			if (result.resCode == 0) {
				return result.accessToken;
			}
		}
		return null;
	}

	public static boolean sendSms(String phone, String code) throws Exception {
		String accessToken = getAccessToken();
		if (accessToken != null) {
			/*
			 * SimpleDateFormat dateFormat = new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String timestamp =
			 * dateFormat.format(new Date());
			 * 
			 * String templateParam = "{\"param1\":\"" + code + "\"}";
			 * 
			 * NameValuePair appIdPair = new NameValuePair("app_id", APP_ID);
			 * NameValuePair tokenPair = new NameValuePair("access_token",
			 * accessToken); NameValuePair telPair = new
			 * NameValuePair("acceptor_tel", phone); NameValuePair
			 * templateIdPair = new NameValuePair("template_id", TEMPLATE_ID);
			 * NameValuePair templateParamPair = new
			 * NameValuePair("template_param", templateParam); NameValuePair
			 * timestampPair = new NameValuePair("timestamp", timestamp); String
			 * body = HttpTool .doPost(SEND_SMS_URL, new NameValuePair[] {
			 * appIdPair, tokenPair, telPair, templateIdPair, templateParamPair,
			 * timestampPair }); if (body != null) { SendSMSResult result = new
			 * Gson().fromJson(body, SendSMSResult.class); if (result.res_code
			 * == 0) { return true; } }
			 */

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String timestamp = dateFormat.format(date);
			Gson gson = new Gson();
			Map<String, String> map = new HashMap<String, String>();
			// 这里存放模板参数，如果模板没有参数直接用template_param={}
			map.put("param1", phone);
			map.put("param2", code);
			map.put("param3", "20");

			String template_param = gson.toJson(map);

//			String postEntity = "app_id=" + APP_ID + "&access_token="
//					+ accessToken + "&acceptor_tel=" + "13735571198" + "&template_id="
//					+ TEMPLATE_ID + "&template_param=" + template_param
//					+ "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
			String postEntity = "acceptor_tel=" + phone + "&template_id="
					+ TEMPLATE_ID + "&template_param=" + template_param + "&app_id="
					+ APP_ID + "&access_token=" + accessToken
					+ "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
			String resJson = "";
			String idertifier = null;
			Map<String, String> map2 = null;
			try {
				resJson = HttpInvoker.httpPost1(SEND_SMS_URL, null, postEntity);
				map2 = gson.fromJson(resJson,
						new TypeToken<Map<String, String>>() {
						}.getType());
				idertifier = map2.get("idertifier").toString();
				
				SendSMSResult result = new Gson().fromJson(resJson, SendSMSResult.class);
				if(result.resCode == 0) {
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		String code = String.valueOf(Math.round(Math.random() * 899999 + 100000)); // 随机生成短信验证码
		boolean isSuccess = SMSUtils.sendSms("18680893391", code);
	}

	class GetTokenResult implements Serializable {
		private static final long serialVersionUID = -6003955917559793675L;
		public int resCode;
		public String resMessage;
		public String accessToken;
		public int expiresIn;

		public int getResCode() {
			return resCode;
		}

		public void setResCode(int resCode) {
			this.resCode = resCode;
		}

		public String getResMessage() {
			return resMessage;
		}

		public void setResMessage(String resMessage) {
			this.resMessage = resMessage;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public int getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(int expiresIn) {
			this.expiresIn = expiresIn;
		}

	}

	class SendSMSResult implements Serializable {
		private static final long serialVersionUID = 4953741204165689933L;
		public int resCode;
		public String resMessage;
		public String idertifier;

		public int getResCode() {
			return resCode;
		}

		public void setResCode(int resCode) {
			this.resCode = resCode;
		}

		public String getResMessage() {
			return resMessage;
		}

		public void setResMessage(String resMessage) {
			this.resMessage = resMessage;
		}

		public String getIdertifier() {
			return idertifier;
		}

		public void setIdertifier(String idertifier) {
			this.idertifier = idertifier;
		}
	}
}