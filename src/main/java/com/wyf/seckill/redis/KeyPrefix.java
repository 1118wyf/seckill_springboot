package com.wyf.seckill.redis;

/**
 * @author: wyf
 * @date:2022/1/9 14:29
 */
public interface KeyPrefix {

    public int expireSecond();
    public String getPrefix();
}
