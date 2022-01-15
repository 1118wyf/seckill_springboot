package com.wyf.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: wyf
 * @date:2022/1/8 16:06
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取单个对象
     */
    //get方法
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;  //jedis 连接池，使用完之后要关闭
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
           String realKey = prefix.getPrefix() + key;
           int seconds = prefix.expireSecond();
           String str = jedis.get(realKey);
           if(seconds <= 0){
               jedis.set(realKey,str);
           } else {
               jedis.setex(realKey,seconds, str);
           }
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     */
    //set方法
    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() == 0){
                return false;
            }
            //生成真正的Key
            String realKey = prefix.getPrefix() + key;
            jedis.set(realKey, str);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断Key是否存在
     */
    //判断Key是否存在
    public <T> boolean exists(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的Key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加
     */
    //增加
    public <T> Long incr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的Key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少
     */
    //减少
    public <T> Long decr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的Key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    //把字符串转化为Bean对象
    private <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length()== 0 || clazz == null){
            return null;
        }
        if(clazz == int.class ||clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else if(clazz == String.class){
            return (T)str;
        }else{
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    //Bean对象转化为String
    private <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class <?> clazz = value.getClass();
        if(clazz == int.class ||clazz == Integer.class){
            return "" + value;
        }else if(clazz == long.class || clazz == Long.class){
            return "" + value;
        }else if(clazz == String.class){
            return (String)value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null){
            jedis.close();
        }
    }

}
