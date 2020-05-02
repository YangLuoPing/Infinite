package com.base.model.elasticsearch.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.model.elasticsearch.entity.TElasticsearchIndex;

import java.util.List;


public interface TElasticsearchIndexService extends IService<TElasticsearchIndex> {

    List<TElasticsearchIndex> findAll(int current, int size, QueryWrapper queryWrapper);

    public List<TElasticsearchIndex> findAllPage(int current, int size, QueryWrapper queryWrapper);
}