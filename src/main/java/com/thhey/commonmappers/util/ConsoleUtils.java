package com.thhey.commonmappers.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制台日志输出工具类
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class ConsoleUtils {
    private static Logger logger = LoggerFactory.getLogger(ConsoleUtils.class);

    public static void print(String title, Object ... objects) {
        System.out.println("======" + title + "======");
        for (Object object : objects) {
            System.out.print(object + "\t");
        }
        System.out.println();
    }

    public static void println(String title, Object ... objects) {
        System.out.println("======" + title + "======");
        for (Object object : objects) {
            System.out.println(object);
        }
        System.out.println();
    }

    public static void info(String title, Object ... objects) {
        if (logger.isInfoEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("======").append(title).append("======");
            for (Object object : objects) {
                builder.append("\n").append(object);
            }
            builder.append("\n");
            logger.info(builder.toString());
        }
    }

    public static void error(String title,Object ... objects){
        if(logger.isErrorEnabled()){
            StringBuilder builder = new StringBuilder();
            builder.append("======").append(title).append("======");
            for (Object object:objects){
                builder.append("\n").append(object);
            }
            builder.append("\n");
            logger.error(builder.toString());
        }
    }
}
