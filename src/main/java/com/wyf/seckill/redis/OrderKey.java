package com.wyf.seckill.redis;

/**
 * @author: wyf
 * @date:2022/1/9 14:40
 */
public class OrderKey extends BasePrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
