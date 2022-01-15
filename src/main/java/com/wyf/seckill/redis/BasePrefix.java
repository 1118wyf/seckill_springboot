package com.wyf.seckill.redis;

/**
 * @author: wyf
 * @date:2022/1/9 14:32
 */
public abstract class BasePrefix implements KeyPrefix {
    //抽象类，有成员变量存放变量值
    private int expireSeconds;
    private String prefix;

    //抽象类abstract不可以new对象，所以可以使用public
    public BasePrefix(String prefix){ //0 默认永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public int expireSecond() {  //默认0代表永不过期
        return expireSeconds;
    }

    //思考如何保证key的prefix不重复呢？
    //可以考虑使用类名
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
