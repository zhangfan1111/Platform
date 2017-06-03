package com.memory.platform.modules.online;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 对在线用户的管理
 * 有增加，移除，获取功能
 * 后面会有更新功能
 * @date 2014-10-20
 */
public class ClientManager {
	
	private static ClientManager instance = new ClientManager();
	
	private ClientManager(){
		
	}
	
	public static ClientManager getInstance(){
		return instance;
	}
	
	private Map<String,Client> clientMap = new HashMap<String, Client>();
	
	/**
	 * 
	 * @param sessionId
	 * @param client
	 */
	public void addClinet(String sessionId,Client client){
		clientMap.put(sessionId, client);
	}
	/**
	 * sessionId
	 */
	public void removeClinet(String sessionId){
		clientMap.remove(sessionId);
	}
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public Client getClient(String sessionId){
		return clientMap.get(sessionId);
	}
	/**
	 * 
	 * @return
	 */
	public Collection<Client> getAllClient(){
		return clientMap.values();
	}

}
