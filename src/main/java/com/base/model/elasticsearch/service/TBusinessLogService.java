package com.base.model.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.model.elasticsearch.entity.TBusinessLog;

/**
 * @Description: 表对应的类型
 * @Date: 2020-05-04
 * @Version: V1.0
 */
public interface TBusinessLogService extends IService<TBusinessLog> {
	public int insert(TBusinessLog tBusinessLog);
}
