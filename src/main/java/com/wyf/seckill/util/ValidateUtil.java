package com.wyf.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: wyf
 * @date:2022/1/10 10:38
 */
public class ValidateUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String str){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String str1 = "15518723825";
        String str2 = "1551872382";
        System.out.println(isMobile(str1));
        System.out.println(isMobile(str2));
    }


}
