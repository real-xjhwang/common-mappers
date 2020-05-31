package com.thhey.commonmappers.mybatis;

/**
 * 处理通用Mapper使用过程中抛出的异常
 *
 * @author xjiehwang@gmail.com
 * @version 1.0
 */
public class BaseException extends Exception {
    public BaseException(String message) {
        super(message);
    }
}
