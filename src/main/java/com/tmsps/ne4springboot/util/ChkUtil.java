package com.tmsps.ne4springboot.util;

import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 各种null校验工具
 *
 * @author 冯晓东 398479251@qq.com
 */
public class ChkUtil {

    public static boolean isNull(Integer num) {
        if (num == null || num == 0) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNull(CharSequence str) {
        if (str == null || "".equals(str.toString().trim())) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNull(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNull(JSONObject json) {
        if (json == null || json.size() == 0) {
            return true;
        }
        Set<String> keys = json.keySet();
        for (String key : keys) {
            Object val = json.get(key);
            if (val == null) {
                continue;
            }
            if ("".equals(val + "")) {
                continue;
            }
            return false;
        }

        return true;
    }// #isNull

    /**
     * 判断对象是否为空 修正之前 object 如果为空字符串情况
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return Objects.isNull(obj) ? true : isNull(obj.toString());
    }// #isNull

    public static boolean isNull(Object... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNull(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNull(String str) {
        if (str == null || "".equals(str.toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }// #isNull

    public static boolean isNotNull(List<?> list) {
        return !isNull(list);
    }// #isNotNull

    public static boolean isNotNull(Object str) {
        return !isNull(str);
    }// #isNotNull

    public static boolean isNotNull(Object... str) {
        return !isNull(str);
    }// #isNotNull

    public static boolean isNotNull(Integer num) {
        return !isNull(num);
    }// #isNotNull

    public static boolean isNotNull(Map<?, ?> map) {
        return !isNull(map);
    }// #isNotNull

    public static boolean isNotNull(JSONObject json) {
        return !isNull(json);
    }// #isNotNull

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean listIsNull(List<?> list) {
        return ChkUtil.isNull(list) || list.isEmpty();
    }// #判断集合为空

    public static boolean listIsNotNull(List<?> list) {
        return !listIsNull(list);
    }

    /**
     * 验证密码
     */
    public static boolean isPassword(String arg) {
        if (isNull(arg)) {
            return false;
        }
        return arg.matches("\\w{6,16}");
    }

    /**
     * 验证手机号码
     */
    public static boolean isMobile(String arg) {
        if (isNull(arg)) {
            return false;
        }
        return arg.matches("13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}");
    }

    public static boolean isIngteger(String arg) {
        if (isNull(arg)) {
            return false;
        }
        return arg.matches("-{0,1}\\d+");
    }

    public static Integer getInteger(String arg) {
        return getInteger(arg, 0);
    }

    public static Integer getInteger(String arg, Integer defaultVal) {
        if (isIngteger(arg)) {
            return Integer.parseInt(arg);
        } else {
            return defaultVal;
        }
    }

    public static BigDecimal getBd(BigDecimal bd) {
        return bd == null ? BigDecimal.ZERO : bd;
    }// #getBd

    /**
     * 如果为空，返回默认值
     */
    public static <T extends CharSequence> T defaultIfNull(final T str, final T defaultVal) {
        return isNull(str) ? defaultVal : str;
    }

    public static String getMap(Map<String, Object> map, String key, String defaultValue) {
        if (ChkUtil.isNull(map)) {
            return defaultValue;
        }
        Object val = map.get(key);
        return ChkUtil.isNotNull(val) ? val.toString() : defaultValue;
    }

    public static String getMapStr(Map<String, String> map, String key, String defaultValue) {
        if (ChkUtil.isNull(map)) {
            return defaultValue;
        }
        Object val = map.get(key);
        return ChkUtil.isNotNull(val) ? val.toString() : defaultValue;
    }

    public static boolean in(int val, int... strs) {
        for (int str : strs) {
            if (val == str) {
                return true;
            }
        }
        return false;
    }

    public static boolean in(String val, String... strs) {
        for (String str : strs) {
            if (val.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
