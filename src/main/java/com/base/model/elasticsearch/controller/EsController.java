package com.base.model.elasticsearch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.model.elasticsearch.entity.TElasticsearchIndex;
import com.base.model.elasticsearch.service.TElasticsearchIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "ES对外接口", tags = {"ES对外接口"})
@RestController
@RequestMapping("/es")
public class EsController {
    @Autowired
    private TElasticsearchIndexService tElasticsearchIndexService;


    @GetMapping("/queryData")
    @ApiOperation("根据条件查询1")
    public List<TElasticsearchIndex> queryData() {
        List<TElasticsearchIndex> list = tElasticsearchIndexService.list(null);
        return list;
    }

    @GetMapping("/queryData1")
    @ApiOperation("根据条件查询2")
    public String queryData1() {
        return "data";
    }

    @GetMapping("/queryEsIndex/{page}/{size}")
    @ApiOperation("查询那些加入了索引")
    public List queryEsIndex(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("indexname").eq("dataname", "producemanager");
        return tElasticsearchIndexService.findAll(page == null ? 0 : page, size == null ? 0 : size, wrapper);
    }
}
