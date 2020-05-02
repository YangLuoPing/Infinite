
package com.base.common.codegenerate.database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.common.codegenerate.generate.pojo.ColumnVo;

public class DbReadTableUtil {
	private static final Logger log = LoggerFactory.getLogger(DbReadTableUtil.class);
	private static Connection conn;
	private static Statement statement;

	public DbReadTableUtil() {
	}

	public static void main(String[] args) throws SQLException {
		try {
			List var1 = selColOption("demo");
			Iterator var2 = var1.iterator();

			while (var2.hasNext()) {
				ColumnVo var3 = (ColumnVo) var2.next();
				System.out.println(var3.getFieldName());
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		PrintStream var10000 = System.out;
		new DbReadTableUtil();
		var10000.println(ArrayUtils.toString(dbConn()));
	}

	/**
	 * 查询对应数据库中的表明
	 * 2020-03-14
	 * 
	 * @return 对应数据库中的表明
	 * @throws SQLException
	 */
	public static List<String> dbConn() throws SQLException {
		/**
		 * 查询对应数据table
		 */
		String selTableSql = null;
		/**
		 * 存放查询的表明
		 */
		ArrayList tabArr = new ArrayList(0);

		try {
			Class.forName(com.base.common.codegenerate.config.dbConfig.drive);
			conn = DriverManager.getConnection(com.base.common.codegenerate.config.dbConfig.url,
					com.base.common.codegenerate.config.dbConfig.userName,
					com.base.common.codegenerate.config.dbConfig.userPwd);
			statement = conn.createStatement(1005, 1007);
			/**
			 * 数据库为MySQL
			 */
			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("mysql")) {
				selTableSql = MessageFormat.format(
						"select distinct table_name from information_schema.columns where table_schema = {0}",
						com.base.common.codegenerate.generate.util.StringBoolUtil
								.strToStr(com.base.common.codegenerate.config.dbConfig.db_filed));
			}
			/**
			 * 数据库为oracle
			 */
			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("oracle")) {
				selTableSql = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("postgresql")) {
				selTableSql = "select tablename from pg_tables where schemaname='public'";
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("sqlserver")) {
				selTableSql = "select distinct url.name as  table_name from sys.objects url where url.type = 'U' ";
			}

			ResultSet resultSet = statement.executeQuery(selTableSql);

			while (resultSet.next()) {
				String var3 = resultSet.getString(1);
				tabArr.add(var3);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
					statement = null;
					System.gc();
				}

				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException var11) {
				throw var11;
			}

		}

		return tabArr;
	}

	/**
	 * 查询 对应表的字段信息
	 * 2020-03-14
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<ColumnVo> selColOption(String tableName) throws Exception {
		String selColsSql = null;
		ArrayList colArr = new ArrayList();
		/**
		 * 表的字段信息
		 */
		ColumnVo cols;
		try {
			Class.forName(com.base.common.codegenerate.config.dbConfig.drive);
			conn = DriverManager.getConnection(com.base.common.codegenerate.config.dbConfig.url,
					com.base.common.codegenerate.config.dbConfig.userName,
					com.base.common.codegenerate.config.dbConfig.userPwd);
			statement = conn.createStatement(1005, 1007);
			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("mysql")) {
				selColsSql = MessageFormat.format(
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName),
						com.base.common.codegenerate.generate.util.StringBoolUtil
								.strToStr(com.base.common.codegenerate.config.dbConfig.db_filed));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("oracle")) {
				selColsSql = MessageFormat.format(
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName.toUpperCase()));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("postgresql")) {
				selColsSql = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName),
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("sqlserver")) {
				selColsSql = MessageFormat.format(
						"select distinct cast(dataType.name as varchar(50)) column_name,  cast(drive.name as varchar(50)) data_type,  cast(userPwd.value as NVARCHAR(200)) comment,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Scale''') as int) num_scale,  dataType.max_length,  (case when dataType.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns dataType left join sys.types drive on dataType.user_type_id=drive.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) url on dataType.object_id=url.object_id left join sys.extended_properties userPwd on userPwd.major_id=url.object_id and userPwd.minor_id=dataType.column_id and userPwd.class=1 where url.name={0} order by dataType.column_id",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName));
			}

			ResultSet resultSet = statement.executeQuery(selColsSql);
			resultSet.last();
			int rows = resultSet.getRow();
			if (rows <= 0) {
				throw new Exception(tableName + "该表不存在或者表中没有字段");
			}

			cols = new ColumnVo();
			/**
			 * 是否对名称进行驼峰法处理
			 */
			if (com.base.common.codegenerate.config.dbConfig.camelCase) {
				cols.setFieldName(strCamelCase(resultSet.getString(1).toLowerCase()));
			} else {
				cols.setFieldName(resultSet.getString(1).toLowerCase());
			}

			cols.setFieldDbName(resultSet.getString(1).toUpperCase());
			cols.setFieldType(strCamelCase(resultSet.getString(2).toLowerCase()));
			cols.setFieldDbType(strCamelCase(resultSet.getString(2).toLowerCase()));
			cols.setPrecision(resultSet.getString(4));
			cols.setScale(resultSet.getString(5));
			cols.setCharmaxLength(resultSet.getString(6));
			cols.setNullable(com.base.common.codegenerate.generate.util.StringBoolUtil.strBool(resultSet.getString(7)));
			colSetOption(cols);
			cols.setFiledComment(
					StringUtils.isBlank(resultSet.getString(3)) ? cols.getFieldName() : resultSet.getString(3));
			/**
			 * 处理页面过滤字段
			 */
			String[] filterFields = new String[0];
			if (com.base.common.codegenerate.config.dbConfig.filterFields != null) {
				filterFields = com.base.common.codegenerate.config.dbConfig.filterFields.toLowerCase().split(",");
			}

			if (!com.base.common.codegenerate.config.dbConfig.dbTableId.equals(cols.getFieldName())
					&& !com.base.common.codegenerate.database.util.StringArrUtil.a(cols.getFieldDbName().toLowerCase(),
							filterFields)) {
				colArr.add(cols);
			}

			while (resultSet.previous()) {
				ColumnVo columns = new ColumnVo();
				if (com.base.common.codegenerate.config.dbConfig.camelCase) {
					columns.setFieldName(strCamelCase(resultSet.getString(1).toLowerCase()));
				} else {
					columns.setFieldName(resultSet.getString(1).toLowerCase());
				}

				columns.setFieldDbName(resultSet.getString(1).toUpperCase());
				if (!com.base.common.codegenerate.config.dbConfig.dbTableId.equals(columns.getFieldName())
						&& !com.base.common.codegenerate.database.util.StringArrUtil
								.a(columns.getFieldDbName().toLowerCase(), filterFields)) {
					columns.setFieldType(strCamelCase(resultSet.getString(2).toLowerCase()));
					columns.setFieldDbType(strCamelCase(resultSet.getString(2).toLowerCase()));
					columns.setPrecision(resultSet.getString(4));
					columns.setScale(resultSet.getString(5));
					columns.setCharmaxLength(resultSet.getString(6));
					columns.setNullable(
							com.base.common.codegenerate.generate.util.StringBoolUtil.strBool(resultSet.getString(7)));
					colSetOption(columns);
					columns.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? columns.getFieldName()
							: resultSet.getString(3));
					colArr.add(columns);
				}
			}
		} catch (ClassNotFoundException exc) {
			throw exc;
		} catch (SQLException sqlexc) {
			throw sqlexc;
		} finally {
			try {
				if (statement != null) {
					statement.close();
					statement = null;
					System.gc();
				}

				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException var16) {
				throw var16;
			}

		}

		ArrayList coList = new ArrayList();

		for (int i = colArr.size() - 1; i >= 0; --i) {
			cols = (ColumnVo) colArr.get(i);
			coList.add(cols);
		}

		return coList;
	}

	public static List<ColumnVo> selColOptions(String var0) throws Exception {
		ResultSet resultSet = null;
		String selSql = null;
		ArrayList colaList = new ArrayList();

		ColumnVo col;
		try {
			Class.forName(com.base.common.codegenerate.config.dbConfig.drive);
			conn = DriverManager.getConnection(com.base.common.codegenerate.config.dbConfig.url,
					com.base.common.codegenerate.config.dbConfig.userName,
					com.base.common.codegenerate.config.dbConfig.userPwd);
			statement = conn.createStatement(1005, 1007);
			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("mysql")) {
				selSql = MessageFormat.format(
						"select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(var0),
						com.base.common.codegenerate.generate.util.StringBoolUtil
								.strToStr(com.base.common.codegenerate.config.dbConfig.db_filed));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("oracle")) {
				selSql = MessageFormat.format(
						" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(var0.toUpperCase()));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("postgresql")) {
				selSql = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(var0),
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(var0));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("sqlserver")) {
				selSql = MessageFormat.format(
						"select distinct cast(dataType.name as varchar(50)) column_name,  cast(drive.name as varchar(50)) data_type,  cast(userPwd.value as NVARCHAR(200)) comment,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Scale''') as int) num_scale,  dataType.max_length,  (case when dataType.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns dataType left join sys.types drive on dataType.user_type_id=drive.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) url on dataType.object_id=url.object_id left join sys.extended_properties userPwd on userPwd.major_id=url.object_id and userPwd.minor_id=dataType.column_id and userPwd.class=1 where url.name={0} order by dataType.column_id",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(var0));
			}

			resultSet = statement.executeQuery(selSql);
			resultSet.last();
			int var4 = resultSet.getRow();
			if (var4 <= 0) {
				throw new Exception("该表不存在或者表中没有字段");
			}

			col = new ColumnVo();
			if (com.base.common.codegenerate.config.dbConfig.camelCase) {
				col.setFieldName(strCamelCase(resultSet.getString(1).toLowerCase()));
			} else {
				col.setFieldName(resultSet.getString(1).toLowerCase());
			}

			col.setFieldDbName(resultSet.getString(1).toUpperCase());
			col.setPrecision(com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(4)));
			col.setScale(com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(5)));
			col.setCharmaxLength(
					com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(6)));
			col.setNullable(com.base.common.codegenerate.generate.util.StringBoolUtil.strBool(resultSet.getString(7)));
			col.setFieldType(
					sqlTypeToJavaType(resultSet.getString(2).toLowerCase(), col.getPrecision(), col.fieldScale()));
			col.setFieldDbType(strCamelCase(resultSet.getString(2).toLowerCase()));
			colSetOption(col);
			col.setFiledComment(
					StringUtils.isBlank(resultSet.getString(3)) ? col.getFieldName() : resultSet.getString(3));
			colaList.add(col);

			while (resultSet.previous()) {
				ColumnVo var7 = new ColumnVo();
				if (com.base.common.codegenerate.config.dbConfig.camelCase) {
					var7.setFieldName(strCamelCase(resultSet.getString(1).toLowerCase()));
				} else {
					var7.setFieldName(resultSet.getString(1).toLowerCase());
				}

				var7.setFieldDbName(resultSet.getString(1).toUpperCase());
				var7.setPrecision(
						com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(4)));
				var7.setScale(
						com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(5)));
				var7.setCharmaxLength(
						com.base.common.codegenerate.generate.util.StringBoolUtil.isBlank(resultSet.getString(6)));
				var7.setNullable(
						com.base.common.codegenerate.generate.util.StringBoolUtil.strBool(resultSet.getString(7)));
				var7.setFieldType(sqlTypeToJavaType(resultSet.getString(2).toLowerCase(), var7.getPrecision(),
						var7.fieldScale()));
				var7.setFieldDbType(strCamelCase(resultSet.getString(2).toLowerCase()));
				colSetOption(var7);
				var7.setFiledComment(
						StringUtils.isBlank(resultSet.getString(3)) ? var7.getFieldName() : resultSet.getString(3));
				colaList.add(var7);
			}
		} catch (ClassNotFoundException var16) {
			throw var16;
		} catch (SQLException var17) {
			throw var17;
		} finally {
			try {
				if (statement != null) {
					statement.close();
					statement = null;
					System.gc();
				}

				if (conn != null) {
					conn.close();
					conn = null;
					System.gc();
				}
			} catch (SQLException var15) {
				throw var15;
			}

		}

		ArrayList var19 = new ArrayList();

		for (int var5 = colaList.size() - 1; var5 >= 0; --var5) {
			col = (ColumnVo) colaList.get(var5);
			var19.add(col);
		}

		return var19;
	}

	/**
	 * 查询表在数据库是否存在
	 * 2020-03-14
	 * @param tableName
	 * @return
	 */
	public static boolean tableExists(String tableName) {
		String var2 = null;

		try {
			log.debug("数据库驱动: " + com.base.common.codegenerate.config.dbConfig.drive);
			Class.forName(com.base.common.codegenerate.config.dbConfig.drive);
			conn = DriverManager.getConnection(com.base.common.codegenerate.config.dbConfig.url,
					com.base.common.codegenerate.config.dbConfig.userName,
					com.base.common.codegenerate.config.dbConfig.userPwd);
			statement = conn.createStatement(1005, 1007);
			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("mysql")) {
				var2 = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '"
						+ tableName + "' and table_schema = '" + com.base.common.codegenerate.config.dbConfig.db_filed
						+ "'";
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("oracle")) {
				var2 = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '"
						+ tableName.toUpperCase() + "'";
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("postgresql")) {
				var2 = MessageFormat.format(
						"select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName),
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName));
			}

			if (com.base.common.codegenerate.config.dbConfig.dataType.equals("sqlserver")) {
				var2 = MessageFormat.format(
						"select distinct cast(dataType.name as varchar(50)) column_name,  cast(drive.name as varchar(50)) data_type,  cast(userPwd.value as NVARCHAR(200)) comment,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Scale''') as int) num_scale,  dataType.max_length,  (case when dataType.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns dataType left join sys.types drive on dataType.user_type_id=drive.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) url on dataType.object_id=url.object_id left join sys.extended_properties userPwd on userPwd.major_id=url.object_id and userPwd.minor_id=dataType.column_id and userPwd.class=1 where url.name={0} order by dataType.column_id",
						com.base.common.codegenerate.generate.util.StringBoolUtil.strToStr(tableName));
			}

			ResultSet var1 = statement.executeQuery(var2);
			var1.last();
			int var3 = var1.getRow();
			return var3 > 0;
		} catch (Exception var4) {
			var4.printStackTrace();
			return false;
		}
	}

	/**
	 * 名称下划线转驼峰法
	 * 2020-03-14
	 * @param str
	 * @return
	 */
	private static String strCamelCase(String str) {
		String[] var1 = str.split("_");
		str = "";
		int var2 = 0;

		for (int var3 = var1.length; var2 < var3; ++var2) {
			if (var2 > 0) {
				String var4 = var1[var2].toLowerCase();
				var4 = var4.substring(0, 1).toUpperCase() + var4.substring(1, var4.length());
				str = str + var4;
			} else {
				str = str + var1[var2].toLowerCase();
			}
		}

		return str;
	}

	/**
	 * 名称下划线转驼峰法
	 * 2020-03-14
	 * @param str
	 * @return
	 */
	public static String camelCase(String var0) {
		String[] var1 = var0.split("_");
		var0 = "";
		int var2 = 0;

		for (int var3 = var1.length; var2 < var3; ++var2) {
			if (var2 > 0) {
				String var4 = var1[var2].toLowerCase();
				var4 = var4.substring(0, 1).toUpperCase() + var4.substring(1, var4.length());
				var0 = var0 + var4;
			} else {
				var0 = var0 + var1[var2].toLowerCase();
			}
		}

		var0 = var0.substring(0, 1).toUpperCase() + var0.substring(1);
		return var0;
	}

	/**
	 * 设置字段的OptionType，ClassType
	 * 2020-03-14
	 * @param column
	 */
	private static void colSetOption(ColumnVo column) {
		/**
		 * 数据类型
		 */
		String fieldType = column.getFieldType();
		/**
		 * 数据规模
		 */
		String fieldScale = column.fieldScale();
		column.setClassType("inputxt");
		if ("N".equals(column.getNullable())) {
			column.setOptionType("*");
		}

		if (!"datetime".equals(fieldType) && !fieldType.contains("time")) {
			if ("date".equals(fieldType)) {
				column.setClassType("easyui-datebox");
			} else if (fieldType.contains("int")) {
				column.setOptionType("n");
			} else if ("number".equals(fieldType)) {
				if (StringUtils.isNotBlank(fieldScale) && Integer.parseInt(fieldScale) > 0) {
					column.setOptionType("userName");
				}
			} else if (!"float".equals(fieldType) && !"double".equals(fieldType) && !"decimal".equals(fieldType)) {
				if ("numeric".equals(fieldType)) {
					column.setOptionType("userName");
				}
			} else {
				column.setOptionType("userName");
			}
		} else {
			column.setClassType("easyui-datetimebox");
		}

	}

	/**
	 * 字段数据类型
	 * 2020-03-14
	 * @param dataType 数据类型
	 * @param var1
	 * @param var2
	 * @return
	 */
	private static String sqlTypeToJavaType(String dataType, String var1, String var2) {
		if (dataType.contains("char")) {
			dataType = "java.lang.String";
		} else if (dataType.contains("int")) {
			dataType = "java.lang.Integer";
		} else if (dataType.contains("float")) {
			dataType = "java.lang.Float";
		} else if (dataType.contains("double")) {
			dataType = "java.lang.Double";
		} else if (dataType.contains("number")) {
			if (StringUtils.isNotBlank(var2) && Integer.parseInt(var2) > 0) {
				dataType = "java.math.BigDecimal";
			} else if (StringUtils.isNotBlank(var1) && Integer.parseInt(var1) > 10) {
				dataType = "java.lang.Long";
			} else {
				dataType = "java.lang.Integer";
			}
		} else if (dataType.contains("decimal")) {
			dataType = "java.math.BigDecimal";
		} else if (dataType.contains("date")) {
			dataType = "java.util.Date";
		} else if (dataType.contains("time")) {
			dataType = "java.util.Date";
		} else if (dataType.contains("blob")) {
			dataType = "byte[]";
		} else if (dataType.contains("clob")) {
			dataType = "java.sql.Clob";
		} else if (dataType.contains("numeric")) {
			dataType = "java.math.BigDecimal";
		} else {
			dataType = "java.lang.Object";
		}

		return dataType;
	}
}
