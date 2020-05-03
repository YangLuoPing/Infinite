package com.base.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自己常用的一些字符串方法
 */
public class MyStringUtil {


	/**
	 * 判断字符串中是否包含中文
	 *
	 * @param str
	 * 		待校验字符串
	 *
	 * @return 是否为中文
	 *
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转换为字符数组
	 *
	 * @param str
	 * 		待转换的字符串
	 *
	 * @return 返回字符数组
	 */
	public static String[] strToStrArr(String str) {
		String[] strArr = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			strArr[i] = str.substring(i, i + 1);
		}
		return strArr;
	}

}
