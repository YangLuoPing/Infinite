package com.base.model.elasticsearch.service;

import java.util.List;

public interface QueryDataService {
	public List queryTempoData(String tableName, Integer page, Integer size);

	public Long queryTempoDataCount(String tableName);

	public List queryProduceData(String tableName, Integer page, Integer size);

	public Long queryProduceDataCount(String tableName);
}
