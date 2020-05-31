package com.thhey.commonmappers.util;

import java.util.Random;
import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class StringUtils {
    /**
     * 数字形式的字符
     */
    public static final String numbersChar = "0123456789";
    /**
     * 数字和大小写字母形式的字符
     */
    public static final String allChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 判断给出的字符串中是否存在空字符串（空字符串、null、纯空格串）
     * @param params 给出的字符串
     * @return 是否存在空字符串
     */
    public static boolean isEmpty(String ... params) {
        for (String string : params) {
            if (string == null || string.isEmpty() || string.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给出的字符串中是否存在不为空的字符串（空字符串、null、纯空格串）
     * @param params 给出的字符串
     * @return 是否存在不为空的字符串
     */
    public static boolean isNotEmpty(String ... params) {
        return !isEmpty(params);
    }

    /**
     * 将字符串的首字母大写
     * @param param 字符串
     * @return 转换后的字符串
     */
    public static String captureName(String param) {
        char[] chars = param.toCharArray();
        chars[0] -= 32;  // 使用ASCII码转换
        return String.valueOf(chars);
    }

    /**
     * 生成指定长度的纯数字字符串
     * @param param 长度
     * @return 生成的数字字符串
     */
    public static String getNumbersString(int param) {
        StringBuffer stringBuffer= new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < param; i++) {
            stringBuffer.append(numbersChar.charAt(random.nextInt(numbersChar.length())));
        }
        return stringBuffer.toString();
    }

    /**
     * 生成指定长度的字符串
     * @param param 长度
     * @return 生成的字符串
     */
    public static String getAllCharString(int param) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < param; i++) {
            stringBuffer.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return stringBuffer.toString();
    }

    /**
     * 生成用户ID
     * @return 用户ID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
