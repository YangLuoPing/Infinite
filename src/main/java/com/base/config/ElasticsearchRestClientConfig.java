package com.base.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestClientConfig {
	@Value("${elastic.hostname}")
	private String hostname;
	@Value("${elastic.port}")
	private Integer port;
	@Value("${elastic.scheme}")
	private String scheme;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost(hostname, port, scheme)));
		return client;
	}
}
