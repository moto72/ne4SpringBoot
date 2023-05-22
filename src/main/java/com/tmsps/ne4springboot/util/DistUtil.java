/**
 * 
 */
package com.tmsps.ne4springboot.util;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class DistUtil {
	private final static double R = 6371229; // 地球的半径 KM

	/**
	 * @param longt1 地理精度1
	 * @param lat1 地理纬度1
	 * @param longt2 地理精度2
	 * @param lat2 地理纬度2
	 * @return 地球上两个坐标点的距离 单位 KM
	 */
	public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
		double x, y;
		x = (longt2 - longt1) * Math.PI * R * Math.cos(((lat1 + lat2) / 2) * Math.PI / 180) / 180;
		y = (lat2 - lat1) * Math.PI * R / 180;
		return Math.hypot(x, y);
	}
}
