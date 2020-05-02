package com.base.model.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.model.elasticsearch.entity.TElasticsearchIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TElasticsearchIndexMapper  extends BaseMapper<TElasticsearchIndex>{
    @Select("SELECT * FROM T_ELASTICSEARCH_INDEX WHERE DATANAME =#{dataname}")
    List<TElasticsearchIndex> findAll(@Param("dataname") String dataname);
}
