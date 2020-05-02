package com.base.leran.mybatisPlus.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.leran.mybatisPlus.entiy.TElasticsearchIndex;

@DS("slave_produce")
public interface TEsIndexMapper extends BaseMapper<TElasticsearchIndex> {
}
