package com.utils.huanxin;

import java.util.HashMap;
import java.util.Map;

public class ErrMsg {
	public static Map<Integer, String> errorMap;
	static {
		errorMap = new HashMap<Integer, String>();
		
		//系统级异常码
		errorMap.put(400, "（错误请求）服务器不理解请求的语法。");
		errorMap.put(401, "（未授权）请求要求身份验证。对于需要token的接口，服务器可能返回此响应。");
		errorMap.put(403, "（禁止）服务器拒绝请求。");
		errorMap.put(404, "（未找到）服务器找不到请求的接口。");
		errorMap.put(408, "（请求超时）服务器等候请求时发生超时。");
		errorMap.put(413, "（请求体过大）请求体超过了5kb，拆成更小的请求体重试即可。");
		errorMap.put(429, "（服务不可用）请求接口超过调用频率限制，即接口被限流。");
		errorMap.put(500, "（服务器内部错误）服务器遇到错误，无法完成请求。");
		errorMap.put(501, "（尚未实施）服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。");
		errorMap.put(502, "（错误网关）服务器作为网关或代理，从上游服务器收到无效响应。");
		errorMap.put(503, "（服务不可用）请求接口超过调用频率限制，即接口被限流。");
		errorMap.put(504, "（网关超时）服务器作为网关或代理，但是没有及时从上游服务器收到请求。");
		
	}
	
	public static String printErrorMsg(final int errorCode) {
		final String eMsg = errorMap.get(errorCode);
		System.out.println("环信错误信息码：" + errorCode + "，错误信息：" + eMsg);
		return eMsg;
	}
}
