package com.base.model.elasticsearch.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.model.elasticsearch.entity.UserColComments;
import com.base.model.elasticsearch.mapper.UserColCommentsMapper;
import com.base.model.elasticsearch.service.UserColCommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserColCommentsServiceImpl extends ServiceImpl<UserColCommentsMapper, UserColComments> implements UserColCommentsService {
	/**
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	@Override
	@DS("slave_tempo")
	public List<UserColComments> findTempoBytableName(String tableName) {
		List<UserColComments> bytableName = baseMapper.findBytableName(tableName);
		return bytableName;
	}

	/**
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	@Override
	@DS("slave_produce")
	public List<UserColComments> findProBytableName(String tableName) {
		List<UserColComments> bytableName = baseMapper.findBytableName(tableName);
		return bytableName;
	}
}
