package com.base.common.codegenerate.database.util;

/**
 * 根据数据库不同使用不同的数据库
 * 
 * @author 15832
 *
 */
public interface DbConvertDef {
	public static final String Y = "Y";

	public static final String N = "N";

	public static final String MySQL = "mysql";

	public static final String Oracle = "oracle";

	public static final String SqlServer = "sqlserver";

	public static final String f = "postgresql";

	public static final String g = "select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION";

	public static final String h = " select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}";

	public static final String i = "select distinct cast(dataType.name as varchar(50)) column_name,  cast(drive.name as varchar(50)) data_type,  cast(userPwd.value as NVARCHAR(200)) comment,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(dataType.object_id,dataType.Name,'''Scale''') as int) num_scale,  dataType.max_length,  (case when dataType.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns dataType left join sys.types drive on dataType.user_type_id=drive.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) url on dataType.object_id=url.object_id left join sys.extended_properties userPwd on userPwd.major_id=url.object_id and userPwd.minor_id=dataType.column_id and userPwd.class=1 where url.name={0} order by dataType.column_id";

	public static final String j = "select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name";

	public static final String k = "select distinct table_name from information_schema.columns where table_schema = {0}";

	public static final String l = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";

	public static final String m = "select distinct url.name as  table_name from sys.objects url where url.type = 'U' ";

	public static final String n = "select tablename from pg_tables where schemaname='public'";
}
