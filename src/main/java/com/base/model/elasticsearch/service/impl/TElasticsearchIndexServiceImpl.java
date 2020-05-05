package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.model.elasticsearch.entity.TElasticsearchIndex;
import com.base.model.elasticsearch.mapper.TElasticsearchIndexMapper;
import com.base.model.elasticsearch.service.TElasticsearchIndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("slave_produce")
@Service
public class TElasticsearchIndexServiceImpl extends ServiceImpl<TElasticsearchIndexMapper, TElasticsearchIndex> implements TElasticsearchIndexService {
	@Override
	public List<TElasticsearchIndex> findAll(int current, int size, QueryWrapper queryWrapper) {
		if (current == 0 && size == 0) {
			return baseMapper.selectList(queryWrapper);
		} else {
			return findAllPage(current, size, queryWrapper);
		}

	}

	@Override
	public List<TElasticsearchIndex> findAllPage(int current, int size, QueryWrapper queryWrapper) {
		Page<TElasticsearchIndex> indexPage = new Page<>(1, 2);
		Page<TElasticsearchIndex> tElasticsearchIndexPage = baseMapper.selectPage(indexPage, null);
		//System.out.println("总共的条数:" + indexPage.getTotal());
		indexPage.getRecords().forEach(System.out::println);
		return indexPage.getRecords();
	}

	@Override
	public List<TElasticsearchIndex> findByDataName(String dataName) {
		QueryWrapper<TElasticsearchIndex> queryWrapper = new QueryWrapper<>();
		if (dataName != null) {
			queryWrapper.in("dataname", dataName.toUpperCase(), dataName.toLowerCase());
		}
		queryWrapper.orderByAsc("dataname", "indexname");
		List<TElasticsearchIndex> list = baseMapper.selectList(queryWrapper);
		return list;
	}

}
