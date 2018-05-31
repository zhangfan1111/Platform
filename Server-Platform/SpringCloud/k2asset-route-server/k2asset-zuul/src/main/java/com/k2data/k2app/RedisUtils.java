package com.k2data.k2app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author cuilibo@k2data.com.cn
 * @Date 17-12-27 下午2:58.
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }
    /**
     * 列表添加
     * @param key
     * @param value
     */
    public void lremove(String key,Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.remove(key,1,value);
    }
    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }
}
