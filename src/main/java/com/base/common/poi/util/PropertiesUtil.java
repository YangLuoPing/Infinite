package com.base.common.poi.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件读取辅助类
 * 
 * @author xuejp
 * 
 */
public class PropertiesUtil {

	private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

	public static Properties prop;

	/**
	 * 初始化读取配置文件
	 */
	public static void ini() {
		prop = new Properties();
		try {
			InputStream is = getResourceAsStream("jdbc.properties");
			if (is != null) {
				prop.load(is);
				is.close();
			}
		} catch (Exception e) {
			log.error("读取配置文件出错,错误信息:" + e.getMessage());
		}
	}

	/**
	 * 读取配置文件，根据键读取值，如果值为Null，则返回空字符串。
	 */
	public static String getText(String key) {
		String value = "";
		try {
			if (prop == null) {
				ini();
			}
			value = prop.getProperty(key);
			if (value == null) {
				return "";
			}
			return value.trim();
		} catch (Exception e) {
			log.error("读取配置文件属性出错,错误信息:" + e.getMessage());
			return value;
		}
	}

	/**
	 * 读取配置文件，根据键读取值，如果值为Null或空，则返回默认值。
	 */
	public static String getText(String key, String defaultValue) {
		String value = "";
		try {
			if (prop == null) {
				ini();
			}
			value = prop.getProperty(key);
			if (value == null || "".equals(value)) {
				if (!"true".equals(value) && !"false".equals(value)) {
					return defaultValue;
				}
			}
			return value.trim();
		} catch (Exception e) {
			log.error("读取配置文件属性出错,错误信息:" + e.getMessage());
			return value;
		}
	}

	/**
	 * 读取配置文件，根据键读取值，返回数字类型
	 */
	public static int getInt(String key, int defaultValue) {
		try {
			if (prop == null) {
				ini();
			}
			return Integer.parseInt(prop.getProperty(key));
		} catch (Exception e) {
			log.error("读取配置文件属性出错,错误信息:" + e.getMessage());
			return defaultValue;
		}
	}

	/**
	 * 读取配置文件，根据键读取值，返回布尔类型
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		try {
			if (prop == null) {
				ini();
			}
			return Boolean.parseBoolean(prop.getProperty(key));
		} catch (Exception e) {
			log.error("读取配置文件属性出错,错误信息:" + e.getMessage());
			return false;
		}
	}

	public static InputStream getResourceAsStream(String resource) throws Exception {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = PropertiesUtil.class.getResourceAsStream(resource);
		}
		if (stream == null) {
			stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(stripped);
		}
		if (stream == null) {
			throw new Exception(resource + " not found");
		}
		return stream;
	}

}
