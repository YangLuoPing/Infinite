package com.base.model.elasticsearch;

import com.base.model.elasticsearch.service.ElasticsearchRestService;
import com.base.model.elasticsearch.service.QueryDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class IndexTest02 {
	@Autowired
	private ElasticsearchRestService elasticsearchRestService;
	@Autowired
	private QueryDataService queryDataService;

	/**
	 * 创建索引
	 *
	 * @throws IOException
	 */
	@Test
	public void createindex() throws IOException {
		elasticsearchRestService.deleteIndex("v_ext_scxt_erp_zhjcs");
		elasticsearchRestService.createIndex("producemanager", "v_ext_scxt_erp_zhjcs");
		elasticsearchRestService.addDocument("producemanager", "v_ext_scxt_erp_zhjcs");
	}

	@Test
	public void querydata() throws IOException {
		Map<String, Object> map = new HashMap<>();
		// "$in":["chejian":"505","505车间"],["jixing":"jixng1","jixing2"]
		Map<String, Object> map2 = new HashMap<>();
		map2.put("XUQIUDANWEI", new String[]{"501", "501车间"});
		map.put("$in", map2);
		map.put("ZERENDANWEI", 505);

		List list = elasticsearchRestService.searchHighlighterIndex("v_ext_scxt_erp_zhjcs", "501", map, "XUQIUDANWEI", false, 1, 300);
		list.forEach(System.out::println);
	}

}
