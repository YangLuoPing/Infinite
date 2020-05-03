package com.base.model.elasticsearch;

import com.base.model.elasticsearch.service.ElasticsearchRestService;
import com.base.model.elasticsearch.service.QueryDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class IndexTest01 {
	@Autowired
	private ElasticsearchRestService elasticsearchRestService;
	@Autowired
	private QueryDataService queryDataService;

	@Test
	public void deleteIndex() throws IOException {
		elasticsearchRestService.deleteIndex("v_ext_scxt_erp_clcs");

	}

	@Test
	public void createindex() throws IOException {
		elasticsearchRestService.deleteIndex("v_ext_scxt_erp_clcs");
		elasticsearchRestService.createIndex("producemanager", "v_ext_scxt_erp_clcs");
		elasticsearchRestService.AddDocument("producemanager", "v_ext_scxt_erp_clcs");
	}

	@Test
	public void createindex2() throws IOException {
		elasticsearchRestService.deleteIndex("v_cjzh_zhj");
		elasticsearchRestService.createIndex("producemanager", "v_cjzh_zhj");
		elasticsearchRestService.AddDocument("producemanager", "v_cjzh_zhj");
	}

	@Test
	public void querydata() {
		queryDataService.queryProduceData("producemanager.v_ext_scxt_erp_clcs", null, null);
	}

}
