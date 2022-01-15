package com.wyf.seckill.util;

import java.util.UUID;

/**
 * @author: wyf
 * @date:2022/1/11 15:42
 */
public class UUIDUtil {

    //生成session的token的方法
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
