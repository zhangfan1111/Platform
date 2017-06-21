package com.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultData {
	
	// 成功
	public static final int SUCCESS = 0;

	// 失败
	public static final int ERROR = 1;

	// 过期
	public static final int EXPIRE = -2;
	
	/**
	 * 返回信息
	 * @author yangshaoping 2016年6月11日 上午10:45:03
	 * @param status
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> getResult(Integer status,String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("status", status);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
	}
	
	/**
	 * 返回信息
	 * @author yangshaoping 2016年6月11日 上午10:46:19
	 * @param status
	 * @param msg
	 * @param data
	 * @return
	 */
	public static Map<String, Object> getResult(Integer status,String msg,Map<String, Object> data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", status);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
	}
	
	/**
	 * status为success
	 * @author yangshaoping 2016年6月11日 下午2:49:58
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> success(String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("status", SUCCESS);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
		
	}
	
	/**
	 * status为success
	 * @author yangshaoping 2016年9月7日 下午5:26:53
	 * @param msg
	 * @param data
	 * @return
	 */
	public static Map<String, Object> success(String msg , Map<String, Object> data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", SUCCESS);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
		
	}
	
	/**
	 * status为success
	 * @author yangshaoping 2016年7月21日 上午10:43:59
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> success(String msg , Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", SUCCESS);
		map.put("msg", msg);
		map.put("data", data == null ? new HashMap<String, Object>() : data);
		
		return map;
	}
	
	/**
	 * status为error
	 * @author yangshaoping 2016年6月11日 下午2:49:58
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> error(String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("status", ERROR);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
		
	}
	
	/**
	 * status为error
	 * @author yangshaoping 2016年9月7日 下午5:25:30
	 * @param msg
	 * @param data
	 * @return
	 */
	public static Map<String, Object> error(String msg,Map<String, Object> data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", ERROR);
		map.put("msg", msg);
		map.put("data", data);
		
		return map;
		
	}
	
	/**
	 * status为error
	 * @author yangshaoping 2016年9月7日 下午5:26:23
	 * @param msg
	 * @param data
	 * @return
	 */
	public static Map<String, Object> error(String msg,Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("status", ERROR);
		map.put("msg", msg);
		map.put("data", data == null ? new HashMap<String, Object>() : data);
		
		return map;
		
	}
	
	/**
	 * token失效
	 * @author yangshaoping 2016年7月19日 下午8:39:32
	 * @return
	 */
	public static Map<String, Object> expire(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("status", EXPIRE);
		map.put("msg", "token失效");
		map.put("data", data);
		
		return map;
		
	}
	
	/**
	 * 系统异常 
	 * @author yangshaoping 2016年6月12日 上午10:13:02
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> systemError(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		map.put("status", ERROR);
		map.put("msg", "系统异常");
		map.put("data", data);
		return map;
		
	}
	
	/**
	 * 系统异常  有异常说明
	 * @author yangshaoping 2016年7月27日 下午3:17:28
	 * @param e
	 * @return
	 */
	public static Map<String, Object> systemError(Exception e){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("errorMsg", e.toString());
		
		map.put("status", ERROR);
		map.put("msg", "系统异常");
		map.put("data", data);
		return map;
	}
	
	/**
	 * 返回easyUI格式
	 * @param total 总共条数
	 * @param rows 分页结果
	 * @return
	 */
	public static Map<String, Object> easyUIListMap(Integer total, List<Map<String, Object>> rows){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", rows);
		return result;
	}
	
}
