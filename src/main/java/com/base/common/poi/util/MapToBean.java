package com.base.common.poi.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToBean {

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(obj, map.get(property.getName()));
			}
		}

		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}

	public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) throws Exception {
		if (map == null)
			return null;
		T bean = beanClass.newInstance();

		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(bean, map.get(property.getName()));
			}
		}

		return bean;
	}

	public static Map<String, Object> beanToMap(Object obj) throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 将 List<Map>对象转化为List<JavaBean>
	 * 
	 * @param listMap
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> convertListMap2ListBean(List<Map<String, Object>> listMap, Class<T> T) throws Exception {
		List<T> beanList = new ArrayList<>();
		if (listMap != null && !listMap.isEmpty()) {
			for (int i = 0, n = listMap.size(); i < n; i++) {
				Map<String, Object> map = listMap.get(i);
				T bean = mapToBean(map, T);
				beanList.add(bean);
			}
			return beanList;
		}
		return beanList;
	}

	/**
	 * 将 List<JavaBean>对象转化为List<Map>
	 * 
	 * @param beanList
	 * @return
	 * @throws Exception
	 */
	public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> beanList, Class<T> T) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (int i = 0, n = beanList.size(); i < n; i++) {
			Object bean = beanList.get(i);
			Map<String, Object> map = beanToMap(bean);
			mapList.add(map);
		}
		return mapList;
	}

}
