package com.tmsps.ne4SpringBoot.utils;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class StrUtil {
	/**
	 * 处理标签字符串，用于初始化
	 */
	public static String getTagsStr(String tags) {
		if (tags.length() == 0) {
			return tags;
		}
		String[] strs = tags.split(",");
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append("'").append(str).append("',");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}

	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}

	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}

	public static String toInStr(String strs) {
		if (ChkUtil.isNull(strs)) {
			return "";
		}
		String origialStr[] = strs.split(",");
		StringBuilder SB = new StringBuilder();
		for (String string : origialStr) {
			SB.append("\'").append(string).append("\'").append(",");
		}
		return SB.subSequence(0, SB.length() - 1).toString();
	}

	public static String toInStr(String... strs) {
		if (ChkUtil.isNull(strs)) {
			return "";
		}
		StringBuilder SB = new StringBuilder();
		for (String string : strs) {
			SB.append("\'").append(string).append("\'").append(",");
		}
		return SB.subSequence(0, SB.length() - 1).toString();
	}

	public static String toLikeOrStr(String strs) {
		String origialStr[] = strs.split(",");
		for (int i = 0; i < origialStr.length; i++) {
			origialStr[i] = "\"%" + origialStr[i] + "%\"";
		}
		return String.join(" or ", origialStr);
	}
}
