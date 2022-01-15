package com.wyf.seckill.redis;

/**
 * @author: wyf
 * @date:2022/1/9 14:39
 */
public class SeckillUserKey extends BasePrefix {

    //此方法转移到BasePrefix中了，所以在此类中可以不用实现
//    private UserKey(String prefix){
//        super(0,prefix);
//    }
    private static final int TOKEN_EXPIRE= 3600 * 24 * 2;
    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE,"tk");

}
