package com.base.model.elasticsearch.service;

import java.util.List;

public interface QueryDataService {
	public List queryTempoData(String tableName, Integer page, Integer Size);

	public List queryProduceData(String tableName, Integer page, Integer Size);
}
