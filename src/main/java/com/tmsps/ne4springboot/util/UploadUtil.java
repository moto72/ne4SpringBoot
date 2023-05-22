package com.tmsps.ne4springboot.util;

import java.io.File;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.util.IdUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author zhangwei 396033084@qq.com
 *
 */
public class UploadUtil {
	static Logger log = LoggerFactory.getLogger(UploadUtil.class);

	public static String _MEDIA_IMG = ".jpg,.jpeg,.png,.gif,.bmp";
	public static String _MEDIA_VIDEO = ".flv,.avi,.mp4";
	public static String _MEDIA_TXT = ".txt";
	public static String BASEPATH = File.separator;
	
	public static String getRealPath(HttpServletRequest request){
		return request.getServletContext().getRealPath("/");
	}
	
	public static String getUploadPath(String savePath) {
		StringBuffer SB = new StringBuffer(savePath);
		SB.append(TimeUtil.getCurrentTime("yyyy/MM/dd/"));
		return SB.toString();
	}

	public static String getUploadPath(String fileName, Date date) {
		StringBuffer SB = new StringBuffer(BASEPATH);
		String fileSuffix = checkSuffix(fileName);// 后缀
		Integer type = MatchType(fileSuffix);// 后缀匹配
		String _timestamp = TimeUtil.getTiemModle(date, "yyyy/MM/dd");
		switch (type) {
		case 0:
			SB.append("pic/").append(_timestamp).append("/");
			break;
		case 1:
			SB.append("video/").append(_timestamp).append("/");
			break;
		case 2:
			SB.append("txt/").append(_timestamp).append("/");
			break;
		default:
			SB.append("other/").append(_timestamp).append("/");
			break;
		}
		return SB.toString();
	}

	/**
	 * 媒体资源类型匹配
	 */
	public static Integer MatchType(String type) {
		if (type == null) {
			return 9;
		} else if (_MEDIA_IMG.contains(type.toLowerCase())) {
			return 0;
		} else if (_MEDIA_VIDEO.contains(type.toLowerCase())) {
			return 1;
		} else if (_MEDIA_TXT.contains(type.toLowerCase())) {
			return 2;
		} else {
			return 9;
		}
	}

	/**
	 * 重新命名资源
	 */
	protected static String reFileName() {
		return IdUtil.getSnowflakeNextIdStr();
	}

	/**
	 * 重新定义文件名称
	 * 
	 */
	public static String getNewFileName(String fileName) {
		StringBuffer SB = new StringBuffer(reFileName());
		SB.append(checkSuffix(fileName));
		return SB.toString();
	}

	/**
	 * 判断FileName的类型
	 */
	public static String checkSuffix(String fileName) {
		int no = fileName.lastIndexOf(".");
		if (no == -1 || no == fileName.length() - 1) {
			return ".nosuffix";
		} else {
			return fileName.substring(no);
		}
	}

	/**
	 * 移动文件
	 */
	public static boolean moveFile(String sourceFile, String targetPath, String newFileName) {
		File file = new File(sourceFile);
		if (!file.exists()) {
			return false;
		}
		File dir = new File(targetPath);
		FileUtil.createDirs(targetPath);
		boolean b = file.renameTo(new File(dir, newFileName));
		return b;
	}

	/**
	 * 根据后缀分类存储类型 目前约定 image , video ,txt 自身判断 other
	 */
	public static String getSuffixType(String fileName) {
		String suffix = checkSuffix(fileName);
		Integer type = MatchType(suffix);
		switch (type) {
		case 0:
			return "image";
		case 1:
			return "video";
		case 2:
			return "txt";
		default:
			return "other";
		}
	}
}
