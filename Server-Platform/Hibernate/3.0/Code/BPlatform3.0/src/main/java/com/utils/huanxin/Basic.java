package com.utils.huanxin;

import org.json.JSONObject;

import com.memory.platform.common.util.HttpTool;


public class Basic {
	/**
	 * token 值
	 */
	public static String token = null;
	
	/**
	 * 有效时间，秒为单位，默认是七天，在有效期内是不需要重复获取的
	 */
	public static long expireTime = 0;
	
	/**
	 * 当前 APP 的 UUID 值
	 */
	private static String application = null;
	
	public static String pwd = "123456";
	
	/**
	 * 使用 APP 的 client_id 和 client_secret 获取授权管理员 token
	 * @author memory 2016年6月1日 上午9:58:24
	 * @return
	 */
	public static boolean isAccessTokenValid() {
		if (token == null || System.currentTimeMillis() > expireTime) {
			try {
				JSONObject params = new JSONObject();
				params.put("grant_type", EndPoints.GRANT_TYPE);
				params.put("client_id", EndPoints.CLIENT_ID);
				params.put("client_secret", EndPoints.CLIENT_SECRET);
				
				JSONObject body = HttpTool.doPosts(EndPoints.TOKEN_APP_URL, params.toString());
				
				printErrorMsg(body);
				
				token = body.getString("access_token");
				expireTime = System.currentTimeMillis() + body.getLong("expires_in") * 1000;
				application = body.getString("application");
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	public static void printErrorMsg(JSONObject obj) {
		if(obj.has("error_description")) {
			System.out.println("错误信息：" + obj.getString("error_description"));
		}
	}
}
