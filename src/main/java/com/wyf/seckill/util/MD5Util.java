package com.wyf.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: wyf
 * @date:2022/1/9 17:50
 */
public class MD5Util {

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "1a2b3c4d";

    //输入的密码转化为表单的密码
    //千万要注意：str = “” + .... 等号后面千万不要忘记有一个空字符串，否则会导致前后端产生的formPass不一致
    public static String inputPassToFormPass(String inputPass){
        String str = ""  + salt.charAt(0) + salt.charAt(2) + salt.charAt(4) + inputPass
                + salt.charAt(1) + salt.charAt(3) + salt.charAt(5);
        return md5(str);
    }

    //将表单提交的密码转化为数据库密码
    public static String formPassToDbPass(String formPass, String saltDb){
        String str = "" + saltDb.charAt(0) + saltDb.charAt(2) + saltDb.charAt(4) + formPass
                + saltDb.charAt(1) + saltDb.charAt(3) + saltDb.charAt(5);
        return md5(str);
    }

    //输入密码转化为数据库存储的密码
    public static String inputPassToDbPass(String inputPass, String saltDb){
       String formPass = inputPassToFormPass(inputPass);
       String dbPass = formPassToDbPass(formPass, saltDb);
       return dbPass;
    }

    //test MD5Util
    public static void main(String[] args) {
        String salt = "1a2b3c4d";
        String inputPass = "123456";
        System.out.println("inputPass = " + inputPass);
        String formPass = inputPassToFormPass(inputPass); //b6cbf730c11247792a5ddd6e056033b0
        System.out.println("formPass = " + formPass);
        String dbPass1 = formPassToDbPass(formPass,salt);
        String dbPass2 = inputPassToDbPass(inputPass,salt);
        System.out.println("dbPass1 = " + dbPass1);
        System.out.println("dbPass2 = " + dbPass2);
    }

}
