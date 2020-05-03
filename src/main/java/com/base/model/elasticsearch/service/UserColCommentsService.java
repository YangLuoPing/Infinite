package com.base.model.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.model.elasticsearch.entity.UserColComments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserColCommentsService extends IService<UserColComments> {
	/**
	 * 获取tempo的列
	 *
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	List<UserColComments> findTempoBytableName(@Param("tableName") String tableName);

	/**
	 * 获取producemanager的列
	 *
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	List<UserColComments> findProBytableName(@Param("tableName") String tableName);
}
