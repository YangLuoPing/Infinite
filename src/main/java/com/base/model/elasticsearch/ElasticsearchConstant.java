package com.base.model.elasticsearch;

/**
 * Elasticsearch 常量
 */
public class ElasticsearchConstant {

	public final static String HOST = "127.0.0.1";
	public final static int PORT = 9200;
	/**
	 * 1秒 连接主机的超时时间（单位：毫秒）
	 */
	public static int CONNECT_TIMEOUT_MILLIS = 1000;
	public static int SOCKET_TIMEOUT_MILLIS = 100000;
	public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
	/**
	 * 1秒 查询的超时时间（单位：毫秒）
	 */
	public static int QUERY_TIMEOUT = 1000;
	/**
	 * 默认不写分页时的查询条数
	 */
	public static int JDBCSIZE = 100;
	/**
	 * 默认一次向es导入数据的条数
	 */
	public static int ESIMPSIZE = 50000;
	/**
	 * 默认不写分页时的查询条数
	 */
	public static int SIZE = 100;
	/**
	 * 是否多个高亮显示，默认为不高亮
	 */
	public static boolean HIGHLIGHT_REQUIREFIELDMATCH = true;
	/**
	 * 高亮前缀
	 */
	public static String HIGHLIGHT_PRETAGS = "<span style='color:red'>";
	/**
	 * 高亮后缀
	 */
	public static String HIGHLIGHT_POSTTAGS = "</span>";


}
