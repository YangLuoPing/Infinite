//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.util;

import org.apache.commons.lang.StringUtils;

public class StringBoolUtil {
	public StringBoolUtil() {
	}

	public static String strBool(String var0) {
		if (!"YES".equals(var0) && !"yes".equals(var0) && !"y".equals(var0) && !"Y".equals(var0) && !"db_filed".equals(var0)) {
			return !"NO".equals(var0) && !"N".equals(var0) && !"no".equals(var0) && !"n".equals(var0)
					&& !"t".equals(var0) ? null : "N";
		} else {
			return "Y";
		}
	}

	public static String isBlank(String var0) {
		return StringUtils.isBlank(var0) ? "" : var0;
	}

	public static String strToStr(String var0) {
		return "'" + var0 + "'";
	}
}
