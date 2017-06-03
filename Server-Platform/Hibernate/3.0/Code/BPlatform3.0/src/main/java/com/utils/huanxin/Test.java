package com.utils.huanxin;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;

public class Test {

	@org.junit.Test
	@Before
	public void test() {
		Basic.isAccessTokenValid();
//		System.out.println(Basic.token);
//		System.out.println(Basic.expireTime);
		assertNotNull(Basic.token);
	}

	@org.junit.Test
	public void testCreateChatRooms() {
		String roomsName = "203039822934180264聊天室";
		String desc = "203039822934180264聊天室，大家快来加入吧";
//		String owner = "402882fc5431c955015431c97d530000";
//		
		List<String> userIds = new ArrayList<String>();
		userIds.add("402882fc54515180015451530aee0000");
//		String roomsId = ChatRooms.createChatRooms(roomsName, desc, owner, userIds);
//		System.out.println(roomsId);
//		assertNotNull(roomsId);
//		
		
		String roomsId = "203039822934180264";
		//修改聊天室信息
//		boolean t = ChatRooms.updateChatRooms(roomsId , roomsName, desc, 200);
//		System.out.println(t);
		
		//批量添加人员
		JSONObject obj = ChatRooms.addChatRoomsMembers(roomsId, userIds);
		System.out.println(obj);
		
		JSONArray array = ChatRooms.chatRoomsInfo(roomsId);
		System.out.println(array.toString());
		
		JSONArray myRoomsInfo = ChatRooms.joinedChatRooms("402882fc54515180015451530aee0000");
		System.out.println(myRoomsInfo.toString());
		
		JSONObject a = ChatRooms.delChatRoomsMembers(roomsId, userIds);
		System.out.println(a.toString());
		
		JSONArray myRoomsInfo1 = ChatRooms.joinedChatRooms("402882fc54515180015451530aee0000");
		System.out.println(myRoomsInfo1.toString());
		
		//获取APP 中所有的聊天室
//		JSONArray roomsList = ChatRooms.chatroomsList();
//		System.out.println(roomsList.toString());
//		
//		for(int i = 0; i < roomsList.length(); i++) {
//			String id = roomsList.getJSONObject(i).getString("id");
//			//删除聊天室
//			boolean a = ChatRooms.deleteChatRooms(id);
//			System.out.println("id : " + id + "(" + a + ")");
//		}
		
		//创建群
//		String r = ChatGroup.createGroup(roomsName, desc, userIds);
//		System.out.println(r);
		
		//注册用户
//		boolean t = Users.registerUser("4028830054a8e7840154a94d06cd0001", Basic.pwd, "18608024067");
//		System.out.println(t);
	}
	
}
