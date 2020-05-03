package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.model.elasticsearch.entity.UserTabColumns;
import com.base.model.elasticsearch.mapper.UserTabColumnsMapper;
import com.base.model.elasticsearch.service.UserTabColumnsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTabColumnsServiceImpl extends ServiceImpl<UserTabColumnsMapper, UserTabColumns> implements UserTabColumnsService {


	@Override
	@DS("slave_tempo")
	public List<UserTabColumns> findTempoBytableName(String tableName) {
		QueryWrapper<UserTabColumns> queryWrapper = queryWrapper = new QueryWrapper<>();
		if (tableName != null) {
			queryWrapper.eq("table_name", tableName.toUpperCase());
		}
		queryWrapper.orderByAsc("table_name", "data_type", "column_name");
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	@DS("slave_produce")
	public List<UserTabColumns> findProBytableName(String tableName) {
		QueryWrapper<UserTabColumns> queryWrapper = queryWrapper = new QueryWrapper<>();
		if (tableName != null) {
			queryWrapper.eq("table_name", tableName.toUpperCase());
		}
		queryWrapper.orderByAsc("table_name", "data_type", "column_name");
		return baseMapper.selectList(queryWrapper);
	}
}
