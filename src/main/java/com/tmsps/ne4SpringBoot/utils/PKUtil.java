/**
 * 2015年6月17日上午11:46:08   2015
 * @author zhangwei  396033084@qq.com
 * @Description: 
 */
package com.tmsps.ne4SpringBoot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class PKUtil {
	/**
	 *======================================================
	 * @author zhangwei 396033084@qq.com 
	 *------------------------------------------------------
	 * 主要用户生产代理主键（PK）使用
	 *======================================================
	 */
	public static String getPK() {
		return GenerateUtil.getBase58ID();
	}
	
	//返回类似201603181601064663899883390订单号
	public static String getOrderNO(){
		return getOrderNO("yyyyMMddHHmmssSSS",10);
	}
	
	public synchronized static String getOrderNO(String datePartten,int randomNumeric) {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sd = new SimpleDateFormat(datePartten);
		return sd.format(new Date()) + RandomStringUtils.randomNumeric(randomNumeric);
	}
}
