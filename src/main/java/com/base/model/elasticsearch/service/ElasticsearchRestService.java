package com.base.model.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticsearchRestService {
	/**
	 * 获取索引的信息
	 *
	 * @param host
	 *
	 * @return
	 */
	public List findAllIndices(String host);

	/**
	 * @param indexName
	 * 		创建的索引
	 *
	 * @throws IOException
	 */
	public void createIndex(String dataName, String indexName);

	/**
	 * 创建所有的索引
	 *
	 * @throws IOException
	 */
	public void createAllIndex();

	/**
	 * 删除指定的索引
	 *
	 * @param indexName
	 * 		删除的索引
	 *
	 * @throws IOException
	 */
	public void deleteIndex(String indexName);

	/**
	 * 删除所有的索引
	 *
	 * @throws IOException
	 */
	public void deleteAllIndex();

	/**
	 * 查看索引是否存在
	 *
	 * @param indexName
	 * 		索引名称
	 *
	 * @return 是否存在
	 */
	public boolean isExistsIndex(String indexName);

	/**
	 * 添加文档
	 *
	 * @param dataName
	 * @param indexName
	 *
	 * @throws IOException
	 */
	public void addDocument(String dataName, String indexName);


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
	public boolean isDocument(String indexName, String id);

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
	public Map getDocumentById(String indexName, String id);

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
	public boolean updateDocumentById(String indexName, String id, Map<String, Object> map);

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
	public boolean deleteDocumentById(String indexName, String id);

	/**
	 * 查询索引的全部文档
	 *
	 * @param indexName
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchIndex(String indexName, Integer page, Integer size);

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
	 * @param params
	 * 		参数
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchIndex(String indexName, String content, Map<String, Object> params, boolean lowerFlag, Integer page, Integer size);

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
	 * @param params
	 * 		参数
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public List searchHighlighterIndex(String indexName, String content, Map<String, Object> params, String higHlightField, boolean lowerFlag, Integer page, Integer size);
}
