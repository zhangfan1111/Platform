package com.utils.tianyi;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
			if (result.res_code == 0) {
				return result.access_token;
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
			System.err.println(timestamp);
			Gson gson = new Gson();
			Map<String, String> map = new HashMap<String, String>();
			// 这里存放模板参数，如果模板没有参数直接用template_param={}
			map.put("param1", phone);
			map.put("param2", code);
			map.put("param3", "20");

			String template_param = gson.toJson(map);
			System.out.println(template_param);

//			String postEntity = "app_id=" + APP_ID + "&access_token="
//					+ accessToken + "&acceptor_tel=" + "13735571198" + "&template_id="
//					+ TEMPLATE_ID + "&template_param=" + template_param
//					+ "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
			String postEntity = "acceptor_tel=" + phone + "&template_id="
					+ TEMPLATE_ID + "&template_param=" + template_param + "&app_id="
					+ APP_ID + "&access_token=" + accessToken
					+ "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
			System.out.println(postEntity);
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
				if(result.res_code == 0) {
					return true;
				}
			} catch (IOException e) {
				System.err.println(resJson);
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println(resJson);
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

		String code = String.valueOf(Math.round(Math.random() * 899999 + 100000)); // 随机生成短信验证码
		boolean isSuccess = SMSUtils.sendSms("18680893391", code);
		System.out.println(isSuccess);
	}

	class GetTokenResult implements Serializable {
		private static final long serialVersionUID = -6003955917559793675L;
		public int res_code;
		public String res_message;
		public String access_token;
		public int expires_in;

		public int getRes_code() {
			return res_code;
		}

		public void setRes_code(int res_code) {
			this.res_code = res_code;
		}

		public String getRes_message() {
			return res_message;
		}

		public void setRes_message(String res_message) {
			this.res_message = res_message;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public int getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}

	}

	class SendSMSResult implements Serializable {
		private static final long serialVersionUID = 4953741204165689933L;
		public int res_code;
		public String res_message;
		public String idertifier;

		public int getRes_code() {
			return res_code;
		}

		public void setRes_code(int res_code) {
			this.res_code = res_code;
		}

		public String getRes_message() {
			return res_message;
		}

		public void setRes_message(String res_message) {
			this.res_message = res_message;
		}

		public String getIdertifier() {
			return idertifier;
		}

		public void setIdertifier(String idertifier) {
			this.idertifier = idertifier;
		}
	}
}