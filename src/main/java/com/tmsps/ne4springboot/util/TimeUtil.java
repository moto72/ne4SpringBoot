package com.tmsps.ne4springboot.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class TimeUtil {
	/**
	 * 将时间转换成需要的格式
	 */
	public static String getTiemModle(Date date, String modle) {
		java.text.SimpleDateFormat sf = new SimpleDateFormat(modle);
		return sf.format(date);
	}

	public static Timestamp getCurrentTimestamp() {
		return Timestamp.valueOf(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 获取当前时间戳
	 */
	public static String getCurrentTime(String model) {
		return TimeUtil.getTiemModle(new Date(), model);
	}

	/**
	 * 获取明天时间
	 */
	public static String getNextTime(String model) {
		return TimeUtil.getTiemModle(new Date((new Date().getTime() + 1 * 24 * 60 * 60 * 1000)), model);
	}

	/**
	 * 获取时间与当前时间差
	 */
	public static long getDiffTime(String frmtime) throws Exception {
		long currnt_time = new Date().getTime();
		java.text.SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long des_time = 0;
		long diff_time = 0;
		try {
			des_time = sf.parse(frmtime).getTime();
		} catch (ParseException e) {
			throw new Exception(e.toString());
		}
		if (des_time > currnt_time) {
			diff_time = (des_time - currnt_time) / 1000;
		}
		return diff_time;
	}

	/**
	 * 在当前时间追加月份
	 * author zhangwei 2015年10月12日 下午7:49:19
	 * @param addmonth
	 * @param model
	 * @return
	 */
	public static String getTimeaddMonth(int addmonth, String model) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, addmonth);
		return TimeUtil.getTiemModle(cal.getTime(), model);
	}
	/**
	 * 在当前时间追加分钟
	 * author zhangwei 2015年10月12日 下午7:48:59
	 * @param count
	 * @param model
	 * @return
	 */
	public static String getTimeaddMinute(int count, String model) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, count);
		return TimeUtil.getTiemModle(cal.getTime(), model);
	}

	/**
	 * 在当前时间追加天数
	 * author zhangwei 2015年10月12日 下午7:49:39
	 * @param addday
	 * @param model
	 * @return
	 */
	public static String getTimeaddDay(int addday, String model) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addday);
		return TimeUtil.getTiemModle(cal.getTime(), model);
	}

	/**
	 * 在特定时间追加月份
	 * author zhangwei 2015年10月12日 下午7:49:47
	 * @param special_date
	 * @param addmonth
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public static String getTimeaddMonth(String special_date, int addmonth, String model) throws ParseException {
		java.text.SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sf.parse(special_date));
		cal.add(Calendar.MONTH, addmonth);
		return TimeUtil.getTiemModle(cal.getTime(), model);
	}

	/**
	 * 在特定时间追加天数
	 * author zhangwei 2015年10月12日 下午7:49:55
	 * @param special_date
	 * @param addday
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public static String getTimeaddDay(String special_date, int addday, String model) throws ParseException {
		java.text.SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sf.parse(special_date));
		cal.add(Calendar.DATE, addday);
		return TimeUtil.getTiemModle(cal.getTime(), model);
	}
}
