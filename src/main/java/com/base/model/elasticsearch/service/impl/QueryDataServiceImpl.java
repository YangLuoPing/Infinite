package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.base.model.elasticsearch.service.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryDataServiceImpl implements QueryDataService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@DS("slave_tempo")
	public List queryTempoData(String tableName, Integer page, Integer Size) {
		String sql = "SELECT * FROM " + tableName;
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	@DS("slave_produce")
	public List queryProduceData(String tableName, Integer page, Integer Size) {
		String sql = "SELECT * FROM " + tableName;
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForList(sql);
	}
}
