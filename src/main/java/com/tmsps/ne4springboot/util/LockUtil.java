package com.tmsps.ne4springboot.util;

/**
 * 事务工具类
 */
public class LockUtil {


    /**
     * 判断多个事务执行,如果不对,抛异常
     */
    public static boolean asserts(boolean... vals) {
        for (boolean val : vals) {
            if (val == false) {
                throw new RuntimeException("更新失败");
            }
        }
        return true;
    }
}
