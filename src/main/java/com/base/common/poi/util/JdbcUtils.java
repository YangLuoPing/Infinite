package com.base.common.poi.util;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
	private static final String DRIVER;
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	static {
		Properties p = new Properties();

		/**
		 * 驱动注册
		 */
		try {
			p.load(JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			// 1、连接池必需要4个连接参数
			DRIVER = p.getProperty("jdbc.driverClassName");
			URL = p.getProperty("jdbc.url");
			USER = p.getProperty("jdbc.username");
			PASSWORD = p.getProperty("jdbc.password");
			Class.forName(DRIVER);
		} catch (ClassNotFoundException | IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 获取 Connetion
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void colseResource(Connection conn, Statement st, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(st);
		closeConnection(conn);
	}

	/**
	 * 释放连接 Connection
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 等待垃圾回收
		conn = null;
	}

	/**
	 * 释放语句执行者 Statement
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 等待垃圾回收
		st = null;
	}

	/**
	 * 释放结果集 ResultSet
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 等待垃圾回收
		rs = null;
	}
}
