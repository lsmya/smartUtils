package com.wxc.serialport.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NetWorkUtils
 * 检测网络
 *
 * @author cq
 * @create by chen in 2016-8-22 17:10:21
 * @modify,
 * @modifytime
 */
public class NetWorkUtils {

    /*Java 验证Ip是否合法*/
    public static boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }
}