package com.base.model.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.base.common.util.HttpUtil;
import com.base.common.util.MapUtils;
import com.base.common.util.MyStringUtil;
import com.base.common.util.idgenerator.UUIDGenerator;
import com.base.model.elasticsearch.ElasticsearchConstant;
import com.base.model.elasticsearch.entity.TBusinessLog;
import com.base.model.elasticsearch.entity.TElasticsearchIndex;
import com.base.model.elasticsearch.entity.UserTabColumns;
import com.base.model.elasticsearch.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ElasticsearchRestServiceImpl implements ElasticsearchRestService {
	@Autowired
	@Qualifier("restHighLevelClient") // 变量名需要bean保持一直否则需要Qualifier注解取到合格者
	private RestHighLevelClient client;
	@Autowired
	private TElasticsearchIndexService tElasticsearchIndexService; // 索引服务
	@Autowired
	private UserTabColumnsService userTabColumnsService;// 表的列类型服务

	@Autowired
	private QueryDataService queryDataService;  // 查询表数据

	@Autowired
	private TBusinessLogService tBusinessLogService; // 插入日志信息

	@Override
	public List findAllIndices(String host) {
		// 发送 GET 请求
		//"http://localhost:9200/_cat/indices"
		String url = host + "/_cat/indices";

		// 从ords获取对应的数据
		String result = HttpUtil.sendGet(url);
		//System.out.println(result);
		// 解析获取到的json数据
		List<String> list = Arrays.asList(result.split("(\\s+)|(\n)"));
		Map<String, String> map = new HashMap<>();
		List<Map<String, String>> resultList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				map.put("color", list.get(i));
			} else {
				if (i % 10 == 2) {
					map.put("index", list.get(i));
				} else if (i % 10 == 6) {
					map.put("count", list.get(i));
				} else if (i % 10 == 8) {
					map.put("size", list.get(i));
				} else if (i % 10 == 0) {
					map.put("color", list.get(i));
					resultList.add(map);
				}
			}

		}
		return resultList;
	}

	@Override
	public void createIndex(String dataName, String indexName) {
		List<UserTabColumns> tabColumns;
		if ("TEMPO".equalsIgnoreCase(dataName)) {
			tabColumns = userTabColumnsService.findTempoBytableName(indexName);
		} else {
			tabColumns = userTabColumnsService.findProBytableName(indexName);
		}
		try {
			// 创建索引请求
			CreateIndexRequest request = new CreateIndexRequest(indexName.toLowerCase());
			//为索引设置一个别名
			request.alias(
					new Alias(indexName.toUpperCase())
			);
			// 指定文档类型  网址：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-create-index.html
			/**
			 * PUT /test2
			 * {
			 *   "mappings": {
			 *     "properties": {
			 *       "name":{
			 *         "type": "text"
			 *       },
			 *       "age":{
			 *         "type": "long"
			 *       }
			 *
			 *     }
			 *   }
			 * }
			 */
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
				builder.startObject("properties");
				{
					for (int i = 0; i < tabColumns.size(); i++) {
						builder.startObject(tabColumns.get(i).getColumnName());
						{
							if (tabColumns.get(i).getDataType() == "DATE") {
								builder.field("type", "data");
							} else {
								builder.field("type", "text");
							}
						}
						builder.endObject();
					}

				}
				builder.endObject();
			}
			builder.endObject();
			request.mapping(builder);
			// 等待时间超时
			request.setTimeout(TimeValue.timeValueMinutes(10));
			// 连接主节点超时
			request.setMasterTimeout(TimeValue.timeValueMinutes(1));
			// 客户端请求 createIndexResponse ，请求后获得响应
			CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
			System.out.println("索引" + indexResponse.index() + "创建成功！");
			addDocument(dataName, indexName);// 添加文档

		} catch (IOException e) {
			System.out.println("索引" + indexName + "创建失败！");
			System.out.println(e.getMessage());

		}


		/*

		Cancellable createIndexResponse = client.indices().createAsync(request, RequestOptions.DEFAULT, new ActionListener<CreateIndexResponse>() {
			@SneakyThrows
			@Override
			public void onResponse(CreateIndexResponse createIndexResponse) {
				AddDocument(dataName, indexName); // 添加文档
				System.out.println("索引" + indexName + "创建成功！");
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("索引" + indexName + "创建失败！");
			}
		});
		*/

	}

	/**
	 * 创建所有的索引
	 *
	 * @throws IOException
	 */
	@Override
	public void createAllIndex() {


		// 获取需要删除的索引
		List<TElasticsearchIndex> tableNameList = tElasticsearchIndexService.findByDataName(null);
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i).getIndexname();
			if (tableName != null) {
				// 创建索引
				createIndex(tableNameList.get(i).getDataname().toUpperCase(), tableName);
			}
		}
	}

	/**
	 * @param indexName
	 * 		删除的索引
	 *
	 * @throws IOException
	 */
	@Override
	public void deleteIndex(String indexName) {
		try {

			if (indexName != null) {
				if (isExistsIndex(indexName)) {
					DeleteIndexRequest request = new DeleteIndexRequest(indexName.toLowerCase());
					AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
					if (delete.isAcknowledged()) {// 是否删除成功
						System.out.println("索引==>" + indexName + "删除成功！");
						log.info("索引==>" + indexName + "删除成功！");
					} else {
						System.out.println("索引==>" + indexName + "删除失败！");
						log.info("索引==>" + indexName + "删除失败！");
					}
				} else {
					System.out.println("索引==>" + indexName + "不存在！");
					log.info("索引==>" + indexName + "不存在！");
				}
			}
		} catch (IOException e) {
			log.info("删除索引==>" + indexName + ":" + e.getMessage());
		}
	}

	/**
	 * 删除所有的索引
	 *
	 * @throws IOException
	 */
	@Override
	public void deleteAllIndex() {
		// 获取需要删除的索引
		List<TElasticsearchIndex> tableNameList = tElasticsearchIndexService.findByDataName(null);
		for (int i = 0; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i).getIndexname();
			if (tableName != null) {
				deleteIndex(tableName);
			}
		}
	}

	/**
	 * @param indexName
	 * 		索引名称
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	@Override
	public boolean isExistsIndex(String indexName) {
		boolean bool = false;
		try {
			GetIndexRequest request = new GetIndexRequest(indexName.toLowerCase());
			bool = client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.info("判断索引是否存在报错" + e.getMessage());
			System.err.println(e.getMessage());
		}
		return bool;
	}

	@Override
	public void addDocument(String dataName, String indexName) {
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("10m");
		//BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

		List list = null;
		try {
			if ("TEMPO".equalsIgnoreCase(dataName)) {
				Long len = queryDataService.queryTempoDataCount(indexName);
				if (len > ElasticsearchConstant.ESIMPSIZE) {
					int lenTemp = (int) Math.ceil(len / ElasticsearchConstant.ESIMPSIZE);
					for (int i = 0; i < lenTemp; i++) {
						System.out.println("page:" + i + "size:" + ElasticsearchConstant.ESIMPSIZE + "导入的总数量：" + i * ElasticsearchConstant.ESIMPSIZE);
						list = queryDataService.queryTempoData(indexName, i, ElasticsearchConstant.ESIMPSIZE);
						impData(list, bulkRequest, indexName);
					}
				} else {
					list = queryDataService.queryTempoData(indexName, null, null);
					impData(list, bulkRequest, indexName);
				}

			} else {
				Long len = queryDataService.queryProduceDataCount(indexName);
				if (len > ElasticsearchConstant.ESIMPSIZE) {
					int lenTemp = (int) Math.ceil(len / ElasticsearchConstant.ESIMPSIZE);
					for (int i = 0; i < lenTemp; i++) {
						System.out.println("page:" + i + 1 + "size:" + ElasticsearchConstant.ESIMPSIZE + "导入的总数量：" + (i + 1) * ElasticsearchConstant.ESIMPSIZE);
						list = queryDataService.queryProduceData(indexName, i + 1, ElasticsearchConstant.ESIMPSIZE);
						impData(list, bulkRequest, indexName);
					}
				} else {
					list = queryDataService.queryProduceData(indexName, null, null);
					impData(list, bulkRequest, indexName);
				}
			}
		} catch (Exception e) {// 查询对应的表数据出错
			TBusinessLog tBusinessLog = new TBusinessLog();
			tBusinessLog.setId(UUIDGenerator.generate());
			tBusinessLog.setLogInfo(e.getMessage());
			tBusinessLog.setBusinessType("elasticsearch");
			tBusinessLog.setCreateTime(new Date(System.currentTimeMillis()));
			tBusinessLogService.insert(tBusinessLog);
			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}

	}

	/**
	 * 导入数据
	 *
	 * @param list
	 * @param bulkRequest
	 * @param indexName
	 */
	public void impData(List list, BulkRequest bulkRequest, String indexName) {
		ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse bulkResponse) {
				//System.out.println("索引" + indexName + "的文档导入成功！");
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("索引" + indexName + "的文档导入失败！");
			}
		};
		// 批处理请求
		for (int i = 0; i < list.size(); i++) {
			// 批量更新和批量删除，就在这里修改对应的请求就可以了
			/*bulkRequest.add(new IndexRequest(indexName).id("" + (i + 1)).source(JSON.toJSONString(list.get(i)),
					XContentType.JSON));*/
			bulkRequest.add(new IndexRequest(indexName).id("" + (i + 1)).source(JSON.toJSONString(list.get(i)),
					XContentType.JSON));
			if (list.size() % 100000 == 0) {// 每10万提交一次
				client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
				bulkRequest.remoteAddress();
			}
		}
	}

	@Override
	public boolean isDocument(String indexName, String id) {
		boolean exists = false;
		try {
			GetRequest getRequest = new GetRequest(indexName, id);
			// 不获取返回的 _source 的上下文了
			getRequest.fetchSourceContext(new FetchSourceContext(false));
			getRequest.storedFields("_none_");
			exists = client.exists(getRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return exists;
	}

	@Override
	public Map getDocumentById(String indexName, String id) {
		Map<String, Object> map = null;
		try {
			GetRequest getRequest = new GetRequest(indexName, id);
			GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			map = getResponse.getSource();
			//System.out.println(getResponse.getSourceAsString()); // 打印文档的内容
			//System.out.println(getResponse); // 返回的全部内容和命令式一样的
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return map;
	}

	@Override
	public boolean updateDocumentById(String indexName, String id, Map<String, Object> map) {
		boolean bool = false;
		try {
			UpdateRequest updateRequest = new UpdateRequest(indexName, id);
			updateRequest.timeout("1s");
			updateRequest.doc(JSON.toJSONString(map), XContentType.JSON);
			UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
			//System.out.println(updateResponse.status());
			if ("OK".equals(updateResponse.status())) {
				bool = true;
			} else {
				bool = false;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return bool;
	}

	@Override
	public boolean deleteDocumentById(String indexName, String id) {
		boolean bool = false;
		try {


			DeleteRequest request = new DeleteRequest(indexName, id);
			request.timeout("1s");
			DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
			System.out.println(deleteResponse.status());
			// NOT_FOUND 未找到
			if ("OK".equals(deleteResponse.status()) || "NOT_FOUND".equals(deleteResponse.status())) {
				bool = true;
			} else {
				bool = false;

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return bool;

	}

	@Override
	public List searchIndex(String indexName, Integer page, Integer size) {
		List resultData = new ArrayList();
		try {
			if (isExistsIndex(indexName)) {// 索引存在时才搜索
				SearchRequest searchRequest = new SearchRequest(indexName);
				// 构建搜索条件
				SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
				if (null != page && null != size) {
					sourceBuilder.from((page - 1) * size);
					sourceBuilder.size(size);
				} else {
					sourceBuilder.size(ElasticsearchConstant.SIZE);
				}
				//执行搜索
				sourceBuilder.timeout(new TimeValue(ElasticsearchConstant.QUERY_TIMEOUT, TimeUnit.SECONDS));
				searchRequest.source(sourceBuilder);
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				System.out.println(JSON.toJSONString(searchResponse.getHits()));
				SearchHits hits = searchResponse.getHits();
				/**
				 * "hits" : {
				 *     "total" : {
				 *       "value" : 5,
				 *       "relation" : "eq"
				 *     },
				 *     "max_score" : 1.0,
				 *     "hits" : [
				 *       {
				 *         "_index" : "v_ext_scxt_erp_clcs",
				 *         "_type" : "_doc",
				 *         "_id" : "1",
				 *         "_score" : 1.0,
				 *         "_source" : {
				 *           "XUQIUDANWEI" : "504",
				 *           "XUQIURIQI" : "20200325",
				 *           "TUOQIFOU" : "拖期",
				 *           "ZERENDANWEI" : "505",
				 *           "KEFARIQI" : "20190325",
				 *           "CHANPINXINGHAO" : "机型8",
				 *           "WANCHENGFOU" : 0
				 *         }
				 *       }
				 *     ]
				 *   }
				 */
				// 结果值
				SearchHit[] searchHits = hits.getHits();
				List datalist = new ArrayList();
				for (SearchHit hit : searchHits) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
					datalist.add(sourceAsMap);
				}
				Map param = new HashMap();
				param.put("total", String.valueOf(hits.getTotalHits()));
				param.put("rows", datalist);
				resultData.add(param);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return resultData;
	}

	@Override
	public List searchHighlighterIndex(String indexName, String content, Map<String, Object> params, String higHlightField, boolean lowerFlag, Integer page, Integer size) {

		List resultData = new ArrayList();
		try {
			if (isExistsIndex(indexName)) {// 索引存在时才搜索
				SearchRequest searchRequest = new SearchRequest(indexName);
				// 构建搜索条件
				SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
				// 查询条件，我们可以使用 QueryBuilders 工具来实现
				// QueryBuilders.termQuery 精确
				// QueryBuilders.matchAllQuery() 匹配所有
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				// boolQueryBuilder2 为filter
				BoolQueryBuilder filterBoolQueryBuilder = QueryBuilders.boolQuery();
				// 若没有查询的内容 ，则全部查询
				// 若没有查询的内容 ，则全部查询
				if (content == null || "".equals(content)) {
					boolQueryBuilder.must(QueryBuilders.matchAllQuery());
				} else {
					boolQueryBuilder.must(QueryBuilders.queryStringQuery(content));

					// 匹配查询
					/*
					 * sourceBuilder.query( QueryBuilders.fuzzyQuery("列明",""));// 模糊匹配
					 */
				}

				if (params.size() > 0) {
					for (Map.Entry<String, Object> param : params.entrySet()) {
						if (param.getValue() != null || !("null".equals(param.getValue().toString()))) {
							// key关键字为 "$in" 则代表为in "$in":{["chejian":"505","505车间"],["jixing":"jixng1","jixing2"]}
							if (param.getKey() == "$in") {// 使用 in 的对象
								Map<String, Object> inQuery = (Map<String, Object>) param.getValue(); //{["chejian":"505","505车间"],["jixing":"jixng1","jixing2"]}

								for (Map.Entry<String, Object> map : inQuery.entrySet()) {

									String[] stringArr = (String[]) map.getValue();
									for (String str : stringArr) {
										if (MyStringUtil.isContainChinese(map.getValue().toString())) {// 中文时需要给列名加 ".keyword" 是保证中文查询正确
											filterBoolQueryBuilder.should(QueryBuilders.termsQuery(map.getKey() + ".keyword", str));
										} else {
											filterBoolQueryBuilder.should(QueryBuilders.termsQuery(map.getKey(), str));
										}
									}


								}
							} else {// 普通参数过滤
								if (MyStringUtil.isContainChinese(param.getValue().toString())) {
									boolQueryBuilder.must(QueryBuilders.termsQuery(param.getKey() + ".keyword", param.getValue().toString()));
								} else {
									boolQueryBuilder.must(QueryBuilders.termQuery(param.getKey(), param.getValue().toString()));
								}
							}

						}
					}
				}
				// 分页
				if (null != page && null != size) {
					sourceBuilder.from((page - 1) * size);
					sourceBuilder.size(size);
				} else {
					sourceBuilder.size(ElasticsearchConstant.SIZE);
				}
				// 构建高亮显示
				HighlightBuilder highlightBuilder = new HighlightBuilder();
				highlightBuilder.field(higHlightField); // 高亮字段
				highlightBuilder.requireFieldMatch(!ElasticsearchConstant.HIGHLIGHT_REQUIREFIELDMATCH);// 多个高亮显示
				highlightBuilder.preTags(ElasticsearchConstant.HIGHLIGHT_PRETAGS);
				highlightBuilder.postTags(ElasticsearchConstant.HIGHLIGHT_POSTTAGS);
				sourceBuilder.highlighter(highlightBuilder);
				// 查询
				boolQueryBuilder.filter(filterBoolQueryBuilder);
				sourceBuilder.query(boolQueryBuilder);
				// 查询后过滤
				// sourceBuilder.postFilter(boolQueryBuilder2);
				// 搜索超时时间
				sourceBuilder.timeout(new TimeValue(ElasticsearchConstant.QUERY_TIMEOUT, TimeUnit.SECONDS));
				// 执行查询
				searchRequest.source(sourceBuilder);
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

				//System.out.println(JSON.toJSONString(searchResponse.getHits()));
				SearchHits hits = searchResponse.getHits();

				// 结果值
				SearchHit[] searchHits = hits.getHits();
				List datalist = new ArrayList();
				for (SearchHit hit : searchHits) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
					// 解析高亮字段，并将原来的字段替换为高亮字段
					Map<String, HighlightField> highlightFields = hit.getHighlightFields();
					HighlightField title = highlightFields.get(higHlightField);
					if (title != null) {
						Text[] fragment = title.fragments();
						String n_title = "";
						for (Text text : fragment) {
							n_title += text;
						}
						sourceAsMap.put(higHlightField, n_title);// 高亮字段替换原来的内容
					}
					if (lowerFlag) {
						datalist.add(MapUtils.transMapLowerCase(sourceAsMap));
					} else {
						datalist.add(sourceAsMap);
					}
				}

				Map hashMap = new HashMap();
				hashMap.put("total", String.valueOf(hits.getTotalHits()));
				hashMap.put("rows", datalist);
				resultData.add(hashMap);
			}
		} catch (Exception e) {
			log.info("查询出错");
			System.err.println(e.getMessage());
		}
		return resultData;
	}


	@Override
	public List searchIndex(String indexName, String content, Map<String, Object> params, boolean lowerFlag, Integer page, Integer size) {
		List resultData = new ArrayList();
		try {
			if (isExistsIndex(indexName)) {// 索引存在时才搜索
				SearchRequest searchRequest = new SearchRequest(indexName);
				// 构建搜索条件
				SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
				// 查询条件，我们可以使用 QueryBuilders 工具来实现
				// QueryBuilders.termQuery 精确
				// QueryBuilders.matchAllQuery() 匹配所有
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				// boolQueryBuilder2 为filter
				BoolQueryBuilder filterBoolQueryBuilder = QueryBuilders.boolQuery();
				// 若没有查询的内容 ，则全部查询
				// 若没有查询的内容 ，则全部查询
				if (content == null || "".equals(content)) {
					boolQueryBuilder.must(QueryBuilders.matchAllQuery());
				} else {
					boolQueryBuilder.must(QueryBuilders.queryStringQuery(content));

					// 匹配查询
					/*
					 * sourceBuilder.query( QueryBuilders.fuzzyQuery("列明",""));// 模糊匹配
					 */
				}

				if (params.size() > 0) {
					for (Map.Entry<String, Object> param : params.entrySet()) {
						if (param.getValue() != null || !("null".equals(param.getValue().toString()))) {
							// key关键字为 "$in" 则代表为in "$in":{["chejian":"505","505车间"],["jixing":"jixng1","jixing2"]}
							if (param.getKey() == "$in") {// 使用 in 的对象
								Map<String, Object> inQuery = (Map<String, Object>) param.getValue(); //{["chejian":"505","505车间"],["jixing":"jixng1","jixing2"]}

								for (Map.Entry<String, Object> map : inQuery.entrySet()) {

									String[] stringArr = (String[]) map.getValue();
									for (String str : stringArr) {
										if (MyStringUtil.isContainChinese(map.getValue().toString())) {// 中文时需要给列名加 ".keyword" 是保证中文查询正确
											filterBoolQueryBuilder.should(QueryBuilders.termsQuery(map.getKey() + ".keyword", str));
										} else {
											filterBoolQueryBuilder.should(QueryBuilders.termsQuery(map.getKey(), str));
										}
									}


								}
							} else {// 普通参数过滤
								if (MyStringUtil.isContainChinese(param.getValue().toString())) {
									boolQueryBuilder.must(QueryBuilders.termsQuery(param.getKey() + ".keyword", param.getValue().toString()));
								} else {
									boolQueryBuilder.must(QueryBuilders.termQuery(param.getKey(), param.getValue().toString()));
								}
							}

						}
					}
				}
				// 分页
				if (null != page && null != size) {
					sourceBuilder.from((page - 1) * size);
					sourceBuilder.size(size);
				} else {
					sourceBuilder.size(ElasticsearchConstant.SIZE);
				}
				// 查询
				boolQueryBuilder.filter(filterBoolQueryBuilder);
				sourceBuilder.query(boolQueryBuilder);
				// 查询后过滤
				// sourceBuilder.postFilter(boolQueryBuilder2);
				// 搜索超时时间
				sourceBuilder.timeout(new TimeValue(ElasticsearchConstant.QUERY_TIMEOUT, TimeUnit.SECONDS));
				// 执行查询
				searchRequest.source(sourceBuilder);
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				//System.out.println(JSON.toJSONString(searchResponse.getHits()));
				SearchHits hits = searchResponse.getHits();
				// 结果值
				SearchHit[] searchHits = hits.getHits();
				List datalist = new ArrayList();
				for (SearchHit hit : searchHits) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
					if (lowerFlag) {
						datalist.add(MapUtils.transMapLowerCase(sourceAsMap));
					} else {
						datalist.add(sourceAsMap);
					}
				}

				Map hashMap = new HashMap();
				hashMap.put("total", String.valueOf(hits.getTotalHits()));
				hashMap.put("rows", datalist);
				resultData.add(hashMap);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			log.info(e.getMessage());
		}
		return resultData;
	}

}
