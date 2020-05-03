package com.base.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestClientConfig {
	@Bean
	public RestHighLevelClient restHighLevelClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						// 默认端口为9200
						new HttpHost("localhost", 9200, "http")));
		return client;
	}
}
