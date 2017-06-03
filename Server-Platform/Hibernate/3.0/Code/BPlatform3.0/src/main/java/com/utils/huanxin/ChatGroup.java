package com.utils.huanxin;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.memory.platform.common.util.HttpTool;

/**
 * <p>
 * id	String	群组 ID，群组唯一标识符，由环信服务器生成。
 * groupid	String	群组 ID，和 id 一样，群组唯一标识符，由环信服务器生成。
 * name	String	群组名称，任意字符串。
 * groupname	String	群组名称，任意字符串。
 * descscription	String	群组描述，任意字符串。
 * public	Boolean	群组类型： true: 公开群，false: 私有群。
 * membersonly	Boolean	是否只有群成员可以进来发言。true: 是，false: 否。
 * allowinvites	Boolean	是否允许群成员邀请别人加入此群。true: 允许群成员邀请人加入此群，false: 只有群主才可以往群里加人。
 * maxusers	Integer	群成员上限，创建群组的时候设置，可修改。
 * affiliations_count	Integer	现有成员总数。
 * affiliations	Array	现有成员列表，包含了 owner 和 member。例如： “affiliations”:[{“owner”: “13800138001”},{“member”:”v3y0kf9arx”},{“member”:”xc6xrnbzci”}]。
 * owner	String	群主的 username。例如：{“owner”: “13800138001”}。
 * member	String	群成员的 username。例如： {“member”:”xc6xrnbzci”}。
 * </p>
 * @author memory 2016年6月2日 下午1:55:33
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2016年6月2日
 * @modify by reason:{方法名}:{原因}
 */
public class ChatGroup {
	/**
	 * 创建群组并加入初选人员
	 * 
	 * @param groupname 群组名称，此属性为必须的\
	 * @param desc 群组描述，此属性为必须的
	 * @param owner 聊天室的管理员，此属性为必须的
	 * @param userIds 群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
	 * @return
	 * @throws IOException
	 */
	public static String createGroup(String groupname, String desc, String owner, List<String> userIds) {
		if (Basic.isAccessTokenValid()) {
			try {
				JSONObject params = new JSONObject();
				params.put("groupname", groupname);
				params.put("desc", desc);
				params.put("public", true); //是否是公开群，此属性为必须的
				params.put("maxusers", 300); //群组成员最大数(包括群主)，值为数值类型，默认值200，此属性为可选的
				params.put("approval", false); //加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
				params.put("owner", owner);
				
				if(userIds.size() > 0) {
					params.put("members", userIds);
				}
				
				JSONObject result = HttpTool.doPostsWithToken(EndPoints.CHATGROUPS_URL, params.toString(), Basic.token);
				Basic.printErrorMsg(result);
				
				JSONObject object = result.getJSONObject("data");
				System.out.println(object.get("groupid"));
				if (!object.isNull("groupid")) {
					return object.getString("groupid");
				}
			} catch (Exception e) {
				return null;
			}

		}
		return null;
	}
}
