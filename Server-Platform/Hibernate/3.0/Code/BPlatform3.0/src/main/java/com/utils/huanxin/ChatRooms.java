package com.utils.huanxin;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.memory.platform.common.util.HttpTool;
import com.memory.platform.common.util.StringUtil;

/**
 * <p>
 *	id	String	聊天室 ID，聊天室唯一标识符，由环信服务器生成。
 *	name	String	聊天室名称，任意字符串。
 *	description	String	聊天室描述，任意字符串。
 *	maxusers	Integer	聊天室成员上限，创建聊天室的时候设置，可修改。
 *	affiliations_count	Integer	现有成员总数。
 *	affiliations	Array	现有成员列表，包含了 owner 和 member。例如 “affiliations”:[{“owner”: “13800138001”},{“member”:”v3y0kf9arx”},{“member”:”xc6xrnbzci”}]。
 *	owner	String	聊天室创建者的 username。例如：{“owner”: “13800138001”}。
 *	member	String	聊天室成员的 username。例如： {“member”:”xc6xrnbzci”}。
 * </p>
 * @author memory 2016年6月1日 上午10:27:17
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2016年6月1日
 * @modify by reason:{方法名}:{原因}
 */
public class ChatRooms {

	/**
	 * 创建聊天室
	 * @author memory 2016年6月1日 上午10:44:02
	 * @param roomsName 聊天室名称，此属性为必须的
	 * @param desc 聊天室描述，此属性为必须的
	 * @param owner 聊天室的管理员，此属性为必须的
	 * @param userIds 聊天室成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主 jma1 不需要写入到 members 里面）
	 * @return String 聊天室id
	 */
	public static String createChatRooms(String roomsName, String desc, String owner, List<String> userIds) {
		if (Basic.isAccessTokenValid()) {
			try {
				JSONObject params = new JSONObject();
				params.put("name", roomsName);
				params.put("description", desc);
				params.put("maxusers", 300); //聊天室成员最大数（包括群主），值为数值类型，默认值200，此属性为可选的
				params.put("owner", owner);
				
				if(userIds.size() > 0) {
					params.put("members", userIds);
				}
				
				JSONObject result = HttpTool.doPostsWithToken(EndPoints.CHATROOM_URL, params.toString(), Basic.token);
				
				Basic.printErrorMsg(result);
				
				JSONObject object = result.getJSONObject("data");
//				System.out.println(object.get("id"));
				if (!object.isNull("id")) {
					return object.getString("id");
				}
			} catch (Exception e) {
				return null;
			}

		}
		return null;
	}
	
	/**
	 * 修改聊天室信息
	 * @author memory 2016年6月1日 上午10:50:28
	 * @param chatroomId 聊天室id
	 * @param roomsName 聊天室名称 
	 * @param description 聊天室描述
	 * @param maxusers 聊天室成员最大数（包括群主），值为数值类型
	 * @return
	 */
	public static boolean updateChatRooms(String chatroomId, String roomsName, String description, int maxusers) {
		if (Basic.isAccessTokenValid()) {
			try {
				String url = EndPoints.CHATROOM_URL + "/" + chatroomId;
				if(StringUtil.isEmpty(description)) {
					description = "update chatroominfo";
				}
				
				maxusers = maxusers <= 0 ? 200 : maxusers;
				
				JSONObject params = new JSONObject();
				params.put("name", roomsName);
				params.put("description", description);
				params.put("maxusers", maxusers);
				
				int status = HttpTool.doPuts(url, params.toString(), Basic.token);
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
	
	/**
	 * 删除聊天室
	 * @author memory 2016年6月1日 上午11:04:58
	 * @param chatroomId 聊天室id
	 * @return
	 */
	public static boolean deleteChatRooms(String chatroomId) {
		if (Basic.isAccessTokenValid()) {
			try {
				String url = EndPoints.CHATROOM_URL + "/" + chatroomId;
				int status = HttpTool.doDelete(url, Basic.token);
				System.out.println(status);
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
	
	/**
	 * 获取 APP 中所有的聊天室
	 * @author memory 2016年6月1日 上午11:15:20
	 * @return
	 */
	public static JSONArray chatroomsList() {
		if (Basic.isAccessTokenValid()) {
			try {
				JSONObject result = HttpTool.doGet(EndPoints.CHATROOM_URL, Basic.token);
				Basic.printErrorMsg(result);
				
				JSONArray array = result.getJSONArray("data");
				return array;
			} catch(Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 获取一个聊天室详情
	 * @author memory 2016年6月1日 上午11:55:21
	 * @param chatroomId 聊天室id
	 * @return
	 */
	public static JSONArray chatRoomsInfo(String chatroomId) {
		if (Basic.isAccessTokenValid()) {
			try {
				String url = EndPoints.CHATROOM_URL + "/" + chatroomId;
				JSONObject result = HttpTool.doGet(url, Basic.token);
				Basic.printErrorMsg(result);
				
				JSONArray array = result.getJSONArray("data");
				return array;
			} catch(Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 获取用户加入的聊天室
	 * @author memory 2016年6月1日 下午12:00:11
	 * @param userId 用户
	 * @return
	 */
	public static JSONArray joinedChatRooms(String userId) {
		if (Basic.isAccessTokenValid()) {
			try {
				String url = EndPoints.USERS_URL + "/" + userId + "/joined_chatrooms";
				JSONObject result = HttpTool.doGet(url, Basic.token);
				Basic.printErrorMsg(result);
				
				JSONArray array = result.getJSONArray("data");
				return array;
			} catch(Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 添加聊天室成员[批量]
	 * @author memory 2016年6月1日 下午12:08:28
	 * @param chatroomId 聊天室id
	 * @param userIds 加入聊天室中的新成员 username
	 * @return
	 */
	public static JSONObject addChatRoomsMembers(String chatroomId, List<String> userIds) {
		if (Basic.isAccessTokenValid()) {
			try {
				String url = EndPoints.CHATROOM_URL + "/" + chatroomId + "/users";
				
				JSONObject params = new JSONObject();
				params.put("usernames", userIds);
				
				JSONObject result = HttpTool.doPostsWithToken(url, params.toString(), Basic.token);
				Basic.printErrorMsg(result);
				
				return result;
			} catch(Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 删除聊天室成员[批量]
	 * @author memory 2016年6月1日 下午12:14:16
	 * @param chatroomId 聊天室id
	 * @param userIds 聊天室中的成员 username
	 * @return
	 */
	public static JSONObject delChatRoomsMembers(String chatroomId, List<String> userIds) {
		if (Basic.isAccessTokenValid()) {
			try {
				String ids = "";
				for(String id : userIds) {
					ids += id + ",";
				}
				ids = ids.substring(0, ids.length() - 1);
				String url = EndPoints.CHATROOM_URL + "/" + chatroomId + "/users/" + ids;
				JSONObject result = HttpTool.doDelete2(url, Basic.token);
				Basic.printErrorMsg(result);
				
				return result;
			} catch(Exception e) {
				return null;
			}
		}
		return null;
	}
}
