package com.ikikyou.practice.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件下载相关工具类
 */
public class HttpUtils {

	/**
	 * 根据文件名获取下载类型
	 *
	 * @param fileName 文件名
	 * @return ContentType
	 */
	public static String getContentTypeByFileName(String fileName) {
		fileName = fileName.toLowerCase();
		return switch (fileName.substring(fileName.lastIndexOf(".") + 1)) {
			case "pdf" -> "application/pdf";
			case "ppt", "pps" -> "application/vnd.ms-powerpoint";
			case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
			case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			case "xls" -> "application/vnd.ms-excel";
			case "doc" -> "application/msword";
			case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			case "jpg", "jpeg" -> "image/jpeg";
			case "png" -> "image/png";
			case "svg" -> "image/svg+xml";
			case "tgz" -> "application/x-compressed";
			case "xml" -> "application/xml";
			case "zip" -> "application/zip";
			case "json" -> "application/json";
			case "html" -> "text/html";
			default -> "application/octet-stream";
		};
	}

	/**
	 * 对文件名进行转换
	 *
	 * @param fileName 文件名
	 * @return URLEncoder转换后的文件名
	 */
	public static String getAttachmentFileName(String fileName){
		String enCodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
		return "attachment; filename=\""+ enCodeFileName +"\"";
	}
}
