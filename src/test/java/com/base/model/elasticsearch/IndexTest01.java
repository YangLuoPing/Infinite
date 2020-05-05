package com.base.model.elasticsearch;

import com.base.model.elasticsearch.entity.TElasticsearchIndex;
import com.base.model.elasticsearch.service.ElasticsearchRestService;
import com.base.model.elasticsearch.service.QueryDataService;
import com.base.model.elasticsearch.service.TElasticsearchIndexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class IndexTest01 {
	@Autowired
	private ElasticsearchRestService elasticsearchRestService;
	@Autowired
	private QueryDataService queryDataService;
	@Autowired
	private TElasticsearchIndexService tElasticsearchIndexService;

	@Test
	public void deleteIndex() throws IOException {
		elasticsearchRestService.deleteIndex("v_ext_scxt_erp_clcs");

	}

	@Test
	public void createindex() throws IOException {
		elasticsearchRestService.deleteIndex("v_ext_scxt_erp_clcs");
		elasticsearchRestService.createIndex("producemanager", "v_ext_scxt_erp_clcs");
		elasticsearchRestService.addDocument("producemanager", "v_ext_scxt_erp_clcs");
	}

	@Test
	public void createindex2() throws IOException {
		elasticsearchRestService.deleteIndex("v_cjzh_zhj");
		elasticsearchRestService.createIndex("producemanager", "v_cjzh_zhj");
		elasticsearchRestService.addDocument("producemanager", "v_cjzh_zhj");
	}

	@Test
	public void querydata() {
		queryDataService.queryProduceData("producemanager.v_ext_scxt_erp_clcs", null, null);
	}

	@Test
	void test() {
		List<TElasticsearchIndex> byDataName = tElasticsearchIndexService.findByDataName(null);
		byDataName.forEach(System.out::println);
	}

	@Test
	void test2() {
		List t_es = queryDataService.queryProduceData("t_es", null, null);
		System.out.println(t_es.size());
	}
}
