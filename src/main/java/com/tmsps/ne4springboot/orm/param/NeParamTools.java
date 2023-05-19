package com.tmsps.ne4springboot.orm.param;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: NeParamTools.java
 * @Package com.tmsps.ne4spring.orm.param
 * @Description: 处理多条件查询时SQL语句最后的生成。
 * @author: zhangwei
 * @date: 2019-08-14
 * @version v1.0
 * @Copyright: 2019 nuoyun All rights reserved.
 */
public class NeParamTools {
	static Logger log = LoggerFactory.getLogger(NeParamTools.class);

	/**
	 * 处理sql语言
	 */
	public static String handleSql(String sql, NeParamList params) {
		// NeParamList 为空，直接返回原始SQL语句
		if (null == params) {
			return sql;
		}
		// sql = sql.replaceAll("\\(\\s*[?]\\s*\\)", "[?]");
		// fix toLowerCase 导致的参数全部小写，致使大小写敏感时导致查询问题
		// sql = sql.replace("[", "@#").replace("]", "#@").toLowerCase();
		sql = sql.replace("[", "@#").replace("]", "#@");
		log.debug("sql.replace:{}", sql);
		StringBuilder sb = new StringBuilder(sql);

		List<NeParam> list = params.getParamList();

		int indexOf = 0;
		for (int index = 0; index < list.size(); index++) {
			NeParam p = list.get(index);
			indexOf = sb.indexOf("?", indexOf);
			if (!p.getIsNull()) {
				indexOf += 1;
				continue;
			}
			// XXX 优化关键字冲突解决代码
			int indexOfL = sb.lastIndexOf(" and ", indexOf);
			if (indexOfL == -1) {
				indexOfL = sb.lastIndexOf(" where ", indexOf) + 1 + 5 + 1;
			} else {
				indexOfL += 1 + 3 + 1;
			}

			String conditionL = sb.substring(indexOfL, indexOf);
			int l = containStr(conditionL, '(');
			int r = containStr(conditionL, ')');

			int indexOfR = indexOf + 1;
			for (; r < l; r++) {
				indexOfR = sb.indexOf(")", indexOfR) + 1;
			}

			sb.insert(indexOfL, "[");
			sb.insert(indexOfR + 1, "]");

			indexOf += 3;
		}

		// 去除大括号
		String dkhReg = "\\[[^\\]]*\\]";
		String made = sb.toString().replaceAll(dkhReg + "\\s+(and)", "").replaceAll("(and)\\s+" + dkhReg, "")
				.replaceAll("(where)\\s+" + dkhReg, "");
		made = made.replace("@#", "[").replace("#@", "]");
		return made;
	}

	public static int containStr(String str, char ch) {
		int cnt = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				cnt++;
			}
		}
		return cnt;
	}
}
