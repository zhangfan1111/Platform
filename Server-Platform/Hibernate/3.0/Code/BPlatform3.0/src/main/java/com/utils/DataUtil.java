package com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>返回值util</p>
 * @author memory 2016年6月21日 下午11:32:14
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2016年6月21日
 * @modify by reason:{方法名}:{原因}
 */
public class DataUtil {
	
	/**
	 * 获取返回值
	 * @author memory 2016年6月21日 下午11:32:25
	 * @param status 状态
	 * @param msg 返回信息
	 * @param data 返回值
	 * @return
	 */
	public static Map<String, Object> getResultMap(Object status, Object msg, Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("msg", msg);
		map.put("data", data);
		return map;
	}
}
