package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.base.model.elasticsearch.ElasticsearchConstant;
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
	public List queryTempoData(String tableName, Integer page, Integer size) {
		if (page == null || page < 0) {
			page = 1;
		}
		size = (size == null ? ElasticsearchConstant.JDBCSIZE : size);
		String sql = "SELECT ROWID RN,ROWNUM ROWNO FROM " + tableName;
		sql = "SELECT t.* FROM (" + sql + " WHERE ROWNUM <= ? ) TEMP LEFT JOIN " + tableName + " T ON TEMP.RN = T.ROWID WHERE TEMP.ROWNO >= ?";
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForList(sql, new Object[]{page * size, (page - 1) * size});
	}

	@Override
	@DS("slave_tempo")
	public Long queryTempoDataCount(String tableName) {
		String sql = "SELECT COUNT(*) FROM " + tableName;
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	@DS("slave_produce")
	public List queryProduceData(String tableName, Integer page, Integer size) {
		if (page == null || page < 0) {
			page = 1;
		}
		size = (size == null ? ElasticsearchConstant.JDBCSIZE : size);
		String sql = "SELECT ROWID RN,ROWNUM ROWNO FROM " + tableName;
		sql = "SELECT T.* FROM (" + sql + " WHERE ROWNUM <= ? ) TEMP LEFT JOIN " + tableName + " T ON TEMP.RN = T.ROWID WHERE TEMP.ROWNO >= ?";
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForList(sql, new Object[]{page * size, (page - 1) * size});
	}

	@Override
	@DS("slave_produce")
	public Long queryProduceDataCount(String tableName) {
		String sql = "SELECT COUNT(*) FROM " + tableName;
		//return jdbcTemplate.queryForList("SELECT * FROM ?", new Object[]{tableName});
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}
