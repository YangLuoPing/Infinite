package com.base.common.poi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {

	private static Context initContext = null;

	private static DataSource ds = null;

	private static final Logger log = LoggerFactory.getLogger(DBConnection.class);

	/**
	 * 创建数据库连接
	 * 
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		String dataSourcAble = PropertiesUtil.getText("datasource.enable");
		if ("false".equals(dataSourcAble)) {
			String driver = PropertiesUtil.getText("jdbc.driverClassName");
			String url = PropertiesUtil.getText("jdbc.url");
			String userName = PropertiesUtil.getText("jdbc.username");
			String passWord = PropertiesUtil.getText("jdbc.password");
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, userName, passWord);
			} catch (ClassNotFoundException e) {
				log.error("创建数据库连接异常,请检查驱动:" + e.getMessage(), e);
			} catch (SQLException e) {
				log.error("创建数据库连接异常,请检查数据库服务是否正常:" + e.getMessage(), e);
			}
			return conn;
		} else if ("true".equals(dataSourcAble)) {
			String dataSourceName = PropertiesUtil.getText("jndi.name");
			try {
				if (initContext == null) {
					initContext = new InitialContext();
				}
				if (ds == null) {
					ds = (DataSource) initContext.lookup(dataSourceName);
				}
				conn = ds.getConnection();
			} catch (NamingException e) {
				log.error("创建数据库链接异常,请检查数据库链接池配置!");
				e.printStackTrace();
			} catch (SQLException e) {
				log.error("创建数据库链接异常,请检查数据库服务器是否正常!");
				e.printStackTrace();
			}
			return conn;
		} else {
			String dataSourceName = PropertiesUtil.getText("jndi.name");
			try {
				if (initContext == null) {
					initContext = new InitialContext();
				}
				if (ds == null) {
					ds = (DataSource) initContext.lookup(dataSourceName);
				}
				conn = ds.getConnection();
			} catch (NamingException e) {
				log.error("创建数据库链接异常,请检查数据库链接池配置!");
				e.printStackTrace();
			} catch (SQLException e) {
				log.error("创建数据库链接异常,请检查数据库服务器是否正常!");
				e.printStackTrace();
			}
			return conn;

		}
	}

	/**
	 * 关闭数据连接
	 * 
	 * @param con
	 */
	public static void realseCon(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			log.error("关闭数据库连接失败!");
			e.printStackTrace();
		}
	}

	/**
	 * 通用方法
	 * 
	 * @param sql
	 * @param conn
	 * @return
	 */
	public static List<Map<String, Object>> queryMapList(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			log.info("queryMapList() sql:" + sql);
			conn = getConn();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String key = rs.getMetaData().getColumnName(i).toLowerCase();
					map.put(key, rs.getObject(key));
				}

				list.add(map);
			}

			return list;
		} catch (SQLException e) {
			log.error("查询信息出错,出错原因:" + e.getMessage(), e);
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("关闭ResultSet出错,出错原因:" + e.getMessage(), e);
					throw e;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					log.error("关闭PreparedStatement出错,出错原因:" + e.getMessage(), e);
					throw e;
				}
			}
			realseCon(conn);
		}
	}

}
