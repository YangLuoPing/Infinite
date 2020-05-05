package com.base.model.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.model.elasticsearch.service.ElasticsearchRestService;
import com.base.model.elasticsearch.service.TElasticsearchIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ES对外接口", tags = {"ES对外接口"})
@RestController
@RequestMapping("/es")
public class EsController {
	@Autowired
	private TElasticsearchIndexService tElasticsearchIndexService;
	@Autowired
	private ElasticsearchRestService elasticsearchRestService;

	@RequestMapping("/queryData")
	@ApiOperation("根据条件查询1")
	public List queryData(@ApiParam("查询参数") @RequestBody() Map<String, Object> requestBody) {
		String index = requestBody.get("index") == null ? "" : requestBody.get("index").toString();
		String content = requestBody.get("content") == null ? "" : requestBody.get("content").toString();
		Integer page = requestBody.get("page") == null ? 0 : (Integer) requestBody.get("page");
		Integer size = requestBody.get("rows") == null ? 0 : (Integer) requestBody.get("rows");
		List list = elasticsearchRestService.searchIndex(index, content, null, false, page, size);
		return list;
	}

	@RequestMapping("/queryData1")
	@ApiOperation("根据条件查询2")
	public List queryData1(@ApiParam("索引名称") @RequestParam(value = "index") String index,
						   @ApiParam("搜索内容") @RequestParam(value = "content") String content,
						   @ApiParam("索引名称") @RequestParam(value = "lowerFlag", required = false) Boolean lowerFlag,
						   @ApiParam("索引名称") @RequestParam(value = "page", required = true) Integer page,
						   @ApiParam("索引名称") @RequestParam(value = "rows", required = true) Integer size,
						   @ApiParam("过滤参数") @RequestParam(value = "params") String params) {
		page = page == null ? 1 : page;
		lowerFlag = lowerFlag == null ? false : lowerFlag;
		HashMap<String, Object> resultparams = new HashMap<>();
		Map<String, Object> map = JSON.parseObject(params);
		for (Map.Entry<String, Object> param : map.entrySet()) {
			if (param.getKey() == "$in") {// 处理 $in
				Map<String, Object> inmaptemp = new HashMap<>();
				Map<String, Object> inmap = (Map<String, Object>) param.getValue();
				for (Map.Entry<String, Object> inparam : inmap.entrySet()) {
					inmaptemp.put(inparam.getKey(), inparam.getValue().toString().split(","));
				}
				resultparams.put("$in", inmaptemp);
			} else {
				resultparams.put(param.getKey(), param.getValue());
			}


		}
		List list = elasticsearchRestService.searchIndex(index, content, resultparams, lowerFlag, page, size);
		return list;
	}

	@GetMapping("/queryEsIndex/{page}/{size}")
	@ApiOperation("查询那些加入了索引")
	public List queryEsIndex(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.isNotNull("indexname").eq("dataname", "producemanager");
		return tElasticsearchIndexService.findAll(page == null ? 0 : page, size == null ? 0 : size, wrapper);
	}

	@GetMapping("/queryEsIndices")
	@ApiOperation("查询索引状态")
	public List queryEsIndex(@Param(value = "host") String host) {
		return elasticsearchRestService.findAllIndices(host);
	}
}
