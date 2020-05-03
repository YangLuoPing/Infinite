package com.base.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

	/**
	 * 转小写
	 *
	 * @param map
	 *
	 * @return
	 */

	public static Map<String, Object> transMapLowerCase(Map<String, Object> map) {
		Map locMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			locMap.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return locMap;
	}

	/**
	 * 转大写
	 *
	 * @param map
	 *
	 * @return
	 */

	public static Map<String, Object> transMapUpperCase(Map<String, Object> map) {
		Map locMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			locMap.put(entry.getKey().toUpperCase(), entry.getValue());
		}
		return locMap;
	}
}
