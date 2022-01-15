package com.wyf.seckill.redis;

/**
 * @author: wyf
 * @date:2022/1/9 14:39
 */
public class UserKey extends BasePrefix {

    //此方法转移到BasePrefix中了，所以在此类中可以不用实现
//    private UserKey(String prefix){
//        super(0,prefix);
//    }

    private UserKey( String prefix) {
        super(prefix);
    }

    public static  UserKey getById = new UserKey("id");
    public static  UserKey getByName = new UserKey("name");



}
