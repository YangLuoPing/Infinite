
package com.base.common.codegenerate.database.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringArrUtil {
	/**
	 * 字符数组处理：给字符串数组中的字符串加单引号和逗号
	 * 
	 * @param paramArrayOfString
	 * @return
	 */
	public static String a(String[] paramArrayOfString) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : paramArrayOfString) {
			if (StringUtils.isNotBlank(str)) {
				stringBuffer.append(",");
				stringBuffer.append("'");
				stringBuffer.append(str.trim());
				stringBuffer.append("'");
			}
		}
		return stringBuffer.toString().substring(1);
	}

	/**
	 * 字符串转大写
	 * 
	 * @param paramString
	 * @return
	 */
	public static String a(String paramString) {
		if (StringUtils.isNotBlank(paramString)) {
			paramString = paramString.substring(0, 1).toLowerCase() + paramString.substring(1);
		}
		return paramString;
	}

	/**
	 * 为数值添加默认值
	 * 
	 * @param paramInteger
	 * @return
	 */
	public static Integer a(Integer paramInteger) {
		if (paramInteger == null) {
			return Integer.valueOf(0);
		}
		return paramInteger;
	}

	/**
	 * 判断字符串是否在字符数组中
	 * 
	 * @param paramString
	 * @param paramArrayOfString
	 * @return
	 */
	public static boolean a(String paramString, String[] paramArrayOfString) {
		if (paramArrayOfString == null || paramArrayOfString.length == 0) {
			return false;
		}
		for (byte b = 0; b < paramArrayOfString.length; b++) {
			String str = paramArrayOfString[b];
			if (str.equals(paramString)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否在字符数组中
	 * 
	 * @param paramString
	 * @param paramList
	 * @return
	 */
	public static boolean a(String paramString, List<String> paramList) {
		String[] arrayOfString = new String[0];
		if (paramList != null) {
			arrayOfString = (String[]) paramList.toArray();
		}

		if (arrayOfString == null || arrayOfString.length == 0) {
			return false;
		}
		for (byte b = 0; b < arrayOfString.length; b++) {
			String str = arrayOfString[b];
			if (str.equals(paramString)) {
				return true;
			}
		}
		return false;
	}
}
