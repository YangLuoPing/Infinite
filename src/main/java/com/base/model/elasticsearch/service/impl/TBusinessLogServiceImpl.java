package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.model.elasticsearch.entity.TBusinessLog;
import com.base.model.elasticsearch.mapper.TBusinessLogMapper;
import com.base.model.elasticsearch.service.TBusinessLogService;
import org.springframework.stereotype.Service;

/**
 * @Description: 日志表
 * @Date: 2020-05-04
 * @Version: V1.0
 */
@Service
@DS("slave_produce")
public class TBusinessLogServiceImpl extends ServiceImpl<TBusinessLogMapper, TBusinessLog> implements TBusinessLogService {


	@Override
	public int insert(TBusinessLog tBusinessLog) {
		int insert = baseMapper.insert(tBusinessLog);
		return insert;
	}
}
