package com.utils.huanxin;

import java.io.IOException;

import org.json.JSONObject;

import com.memory.platform.common.util.HttpTool;

public class Users {
	/**
	 * 创建IM用户(单个)
	 * 
	 * @param username 用户 名
	 * @param password 密码
	 * @param nickName 昵称
	 * @return
	 * @throws IOException
	 */
	public static boolean registerUser(String username, String password, String nickName) {
		if (Basic.isAccessTokenValid()) {
			try {
//				String params = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
				JSONObject params = new JSONObject();
				params.put("username", username);
				params.put("password", password);
				params.put("nickname", nickName);
				
				int status = HttpTool.doPosts(EndPoints.USERS_URL, params.toString(), Basic.token);
				if (status == 200) {
					return true;
				} else {
					ErrMsg.printErrorMsg(status);
					return false;
				}
			} catch (Exception e) {
				return false;
			}

		}
		return false;

	}
}
