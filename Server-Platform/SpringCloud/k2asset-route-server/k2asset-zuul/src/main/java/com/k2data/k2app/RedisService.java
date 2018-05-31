package com.k2data.k2app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author cuilibo@k2data.com.cn
 * @Date 17-12-27 下午2:58.
 */
@Service
public class RedisService {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 保存多个客户端信息，一个用户可以登陆多个设备
     * @param loginName
     * @param token
     */
    public void saveUserToken(String loginName,String token) {
        redisUtils.lPush("users",loginName+"###"+token);
    }

    /**
     * 保持单个客户端信息，一个用户登陆一个设备，一旦登陆，之前的退出
     * @param loginName
     * @param token
     */
    public void saveUser(String loginName,String token) {
        userOut(loginName);
        redisUtils.lPush("users",loginName+"###"+token);
    }

    public boolean exitUserToken(String token) {
        boolean flag = false;
        List<Object> users = redisUtils.lRange("users", 0, 1000);
        for (int i = 0; i < users.size() ; i++) {
            Object s = users.get(i);
            if(s.toString().split("###")[1].equals(token)){
                flag = true;
            }
        }
        return flag;
    }
    public void layoutUser(String loginName,String token) {
        redisUtils.lremove("users",loginName+"###"+token);
    }

    public void tokenTimeout(String token) {
        List<Object> users = redisUtils.lRange("users", 0, 1000);
        users.forEach(s->{
            if(s.toString().split("###")[1].equals(token)){
                redisUtils.lremove("users",s.toString());
            }
        });
    }
    public void userOut(String userName) {
        List<Object> users = redisUtils.lRange("users", 0, 1000);
        users.forEach(s->{
            if(s.toString().split("###")[0].equals(userName)){
                redisUtils.lremove("users",s.toString());
            }
        });
    }
}
