package com.base.model.elasticsearch.service.impl;

import com.base.model.elasticsearch.service.ElasticsearchRestService;
import com.base.model.elasticsearch.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduledServiceImpl implements ScheduledService {
	@Value("${elastic.hostname}")
	private String hostname;
	@Value("${elastic.port}")
	private Integer port;
	@Value("${elastic.scheme}")
	private String scheme;

	@Autowired
	private ElasticsearchRestService elasticsearchRestService;


	//秒 分 时 日   月   周几
	//0 * * * * MON-FRI
	//注意cron表达式的用法；
	//@Scheduled(cron = "${elastic.timingcron}")
	@Scheduled(initialDelayString = "${elastic.initialDelay}", fixedDelayString = "${elastic.fixedDelay}")
	@Override
	public void elasticsearch() {
		System.out.println("elasticsearch定时任务timing.....");
		elasticsearchRestService.deleteAllIndex();
		elasticsearchRestService.createAllIndex();
		String url = scheme + "://" + hostname + ":" + port;
		List<Map<String, String>> list = elasticsearchRestService.findAllIndices(url);
		list.forEach(System.out::println);


	}
}
