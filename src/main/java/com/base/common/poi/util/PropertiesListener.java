package com.base.common.poi.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件读取类
 * 
 * @author xuejp
 * 
 */
public class PropertiesListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(PropertiesListener.class);

	/**
	 * 应用服务启动时调用
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 获取程序在服务器上的部署路径
		try {
			PropertiesUtil.ini();
		} catch (Exception e) {
			log.error("初始化组件参数失败：" + e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
