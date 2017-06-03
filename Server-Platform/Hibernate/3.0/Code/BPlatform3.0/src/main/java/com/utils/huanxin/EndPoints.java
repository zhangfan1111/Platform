package com.utils.huanxin;

public interface EndPoints {

	public static final String HUANXIN_URL = "https://a1.easemob.com/57603418-2/beautycircle";

	public static final String TOKEN_APP_URL = HUANXIN_URL + "/token";

	//用户体系集成url
	public static final String USERS_URL = HUANXIN_URL + "/users";

	//环信消息url
	public static final String MESSAGES_URL = HUANXIN_URL + "/messages";

	//群组url
	public static final String CHATGROUPS_URL = HUANXIN_URL + "/chatgroups";

	//群组消息url
	public static final String CHATMESSAGES_URL = HUANXIN_URL + "/chatmessages";
	
	//聊天室url
	public static final String CHATROOM_URL = HUANXIN_URL + "/chatrooms";

	public static final String CLIENT_ID = "YXA6bGwQcCfJEeabV78ji68R5Q";
	
	public static final String CLIENT_SECRET = "YXA60njA-T1htwxZLTRW2ONUmBPamEo";
	
	public static final String GRANT_TYPE = "client_credentials";
}
