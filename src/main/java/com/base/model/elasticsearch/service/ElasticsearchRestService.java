package com.base.model.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticsearchRestService {
	/**
	 * @param indexName
	 * 		创建的索引
	 *
	 * @throws IOException
	 */
	public void createIndex(String dataName, String indexName) throws IOException;

	/**
	 * 创建所有的索引
	 *
	 * @throws IOException
	 */
	public void createAllIndex() throws IOException;

	/**
	 * 删除指定的索引
	 *
	 * @param indexName
	 * 		删除的索引
	 *
	 * @throws IOException
	 */
	public void deleteIndex(String indexName) throws IOException;

	/**
	 * 删除所有的索引
	 *
	 * @throws IOException
	 */
	public void deleteAllIndex() throws IOException;

	/**
	 * 查看索引是否存在
	 *
	 * @param indexName
	 * 		索引名称
	 *
	 * @return 是否存在
	 */
	public boolean isExistsIndex(String indexName) throws IOException;

	/**
	 * 添加文档
	 *
	 * @param dataName
	 * @param indexName
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean AddDocument(String dataName, String indexName) throws IOException;


	/**
	 * 查看文档是否存在
	 *
	 * @param indexName
	 * 		索引名称
	 * @param id
	 * 		id
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean isDocument(String indexName, String id) throws IOException;

	/**
	 * 根据id获取文档
	 *
	 * @param indexName
	 * 		索引名称
	 * @param id
	 * 		id
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public Map getDocumentById(String indexName, String id) throws IOException;

	/**
	 * 根据id获取文档
	 *
	 * @param indexName
	 * 		索引名称
	 * @param id
	 * 		id
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean updateDocumentById(String indexName, String id, Map<String, Object> map) throws IOException;

	/**
	 * 根据id删除文档
	 *
	 * @param indexName
	 * @param id
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean deleteDocumentById(String indexName, String id) throws IOException;

	/**
	 * 查询索引的全部文档
	 *
	 * @param indexName
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchIndex(String indexName, Integer page, Integer size) throws IOException;

	/**
	 * 搜索
	 *
	 * @param indexName
	 * 		索引名称
	 * @param content
	 * 		搜索字段
	 * @param lowerFlag
	 * 		返回列名是否转小写
	 * @param page
	 * 		页码
	 * @param size
	 * 		页大小
	 * @param param
	 * 		参数
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchIndex(String indexName, String content, Map<String, Object> params, boolean lowerFlag, Integer page, Integer size) throws IOException;

	/**
	 * 高亮搜索
	 *
	 * @param indexName
	 * 		索引名称
	 * @param content
	 * 		搜索字段
	 * @param higHlightField
	 * 		高亮字段名称
	 * @param lowerFlag
	 * 		返回列名是否转小写
	 * @param page
	 * 		页码
	 * @param size
	 * 		页大小
	 * @param param
	 * 		参数
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchHighlighterIndex(String indexName, String content, Map<String, Object> params, String higHlightField, boolean lowerFlag, Integer page, Integer size) throws IOException;
}
