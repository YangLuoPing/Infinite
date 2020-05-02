package com.base.common.codegenerate.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class dbConfig {
	private static Properties r = new Properties();
	private static Properties s = new Properties();
	public static String dataType = "mysql";

	public static String drive = "com.mysql.jdbc.Driver";

	public static String url = "jdbc:mysql://localhost:3308/jeecg-boot?useUnicode=true&characterEncoding=UTF-8";

	public static String userName = "root";

	public static String userPwd = "root";
	/**
	 * Mysql数据库名,oracle的用户名
	 */
	public static String db_filed = "";
	/**
	 * 工程位置
	 */
	public static String projectPath = "";
	/**
	 * 包名
	 */
	public static String projectPackage = "com.ylg";

	public static String src = "src";
	/**
	 * webroot
	 */
	public static String webRoot = "WebRoot";
	/**
	 * 模板位置
	 */
	public static String templatePath = "";
	/**
	 * 是否下划线转驼峰命名法
	 */
	public static boolean camelCase = true;

	public static String dbTableId;

	public static String n = "4";

	public static String page_search_filed_num = "3";
	/**
	 * 页面过滤字段
	 */
	public static String filterFields;

	public static String q = "1";

	static {
		// 使用ClassLoader加载properties配置文件生成对应的输入流

		InputStream config = dbConfig.class.getClassLoader().getResourceAsStream("codegenerte/coderConfig/codegenerte_config.properties");
		InputStream database = dbConfig.class.getClassLoader()
				.getResourceAsStream("codegenerte/coderConfig/codegenerte_database.properties");

		// 使用properties对象加载输入流

		try {
			r.load(database);
			s.load(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		drive = a();
		url = b();
		userName = c();
		userPwd = d();
		db_filed = e();

		src = g();
		webRoot = h();
		projectPackage = o();
		templatePath = p();
		projectPath = m();

		dbTableId = i();
		camelCase = camelCase();

		filterFields = j();

		page_search_filed_num = k();

		if (url.indexOf("mysql") >= 0 || url.indexOf("MYSQL") >= 0) {
			dataType = "mysql";
		} else if (url.indexOf("oracle") >= 0 || url.indexOf("ORACLE") >= 0) {
			dataType = "oracle";
		} else if (url.indexOf("postgresql") >= 0 || url.indexOf("POSTGRESQL") >= 0) {
			dataType = "postgresql";
		} else if (url.indexOf("sqlserver") >= 0 || url.indexOf("sqlserver") >= 0) {
			dataType = "sqlserver";
		}

		src = src.replace(".", "/");
		webRoot = webRoot.replace(".", "/");
	}

	private void n() {
	}

	public static final String a() {
		return r.getProperty("diver_name");
	}

	public static final String b() {
		return r.getProperty("url");
	}

	public static final String c() {
		return r.getProperty("username");
	}

	public static final String d() {
		return r.getProperty("password");
	}

	public static final String e() {
		return r.getProperty("database_name");
	}

	public static final boolean camelCase() {
		String str = s.getProperty("db_filed_convert");
		if (str.toString().equals("false")) {
			return false;
		}
		return true;
	}

	private static String o() {
		return s.getProperty("bussi_package");
	}

	private static String p() {
		return s.getProperty("templatepath");
	}

	public static final String g() {
		return s.getProperty("source_root_package");
	}

	public static final String h() {
		return s.getProperty("webroot_package");
	}

	public static final String i() {
		return s.getProperty("db_table_id");
	}

	public static final String j() {
		return s.getProperty("page_filter_fields");
	}

	public static final String k() {
		return s.getProperty("page_search_filed_num");
	}

	public static final String l() {
		return s.getProperty("page_field_required_num");
	}

	public static String m() {
		String str = s.getProperty("project_path");
		if (str != null && !"".equals(str)) {
			projectPath = str;
		}
		return projectPath;
	}

	public static void a(String paramString) {
		projectPath = paramString;
	}

	public static void setTemplatePath(String paramString) {
		templatePath = paramString;
	}

	public static void main(String[] args) {
		dbConfig dbConfig = new dbConfig();

	}
}
