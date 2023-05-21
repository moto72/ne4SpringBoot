package com.tmsps.ne4springboot.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @ClassName: NeDateUtil
 * @Description:
 * @author: zhangwei(Mr.z).396033084@qq.com @date： 2023/03/24
 * @Copyright: 行歌信息
 */
public class NeDateUtil {
	static String startTime = "00:00:00";
	static String endTime = "23:59:59";
	
	public static String getToday() {
		return StrUtil.join(StrUtil.SPACE, DateUtil.today(), startTime);
	}

	public static String getTodayEnd() {
		return StrUtil.join(StrUtil.SPACE, DateUtil.today(), endTime);
	}

	public static String getYesterday() {
		return StrUtil.join(StrUtil.SPACE, DateUtil.yesterday().toDateStr(), startTime);
	}

	public static String getYesterdayEnd() {
		return StrUtil.join(StrUtil.SPACE, DateUtil.yesterday().toDateStr(), endTime);
	}
	
	public static String getMonthStart() {
		return StrUtil.join(StrUtil.SPACE, LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), startTime);
	}
	
	public static String getMonthEnd() {
		return StrUtil.join(StrUtil.SPACE, LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), endTime);
	}
	
	
	public static Date localDateToSqlDate(final LocalDate localDate) {
		if (null == localDate) {
			return null;
		}
		return Date.valueOf(localDate);
	}
	
	public static Date addDays(LocalDate localDate, Integer days) {
		if (null == localDate) {
			localDate = LocalDate.now();
		}
		if (null == days) {
			days = 0;
		}
		return localDateToSqlDate(localDate.plusDays(days));
	}
	
	/**
	 * 	@Description: 将时间转换为时间的毫秒数(13bit)
	 *	@author: zhangwei(Mr.z).396033084@qq.com
	 *	@date： 2023/05/13
	 */
	public static String datetimeToTimeMillis(String datetimeStr) {
		if (StrUtil.isBlank(datetimeStr)) {
			return null;
		}
		Long timeMillis = DateTime.of(datetimeStr, DatePattern.NORM_DATETIME_PATTERN).getTime();
		return timeMillis.toString();
	}
}
