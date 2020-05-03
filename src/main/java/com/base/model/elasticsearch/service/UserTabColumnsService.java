package com.base.model.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.model.elasticsearch.entity.UserTabColumns;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTabColumnsService extends IService<UserTabColumns> {
	/**
	 * 获取tempo的列
	 *
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	List<UserTabColumns> findTempoBytableName(@Param("tableName") String tableName);

	/**
	 * 获取producemanager的列
	 *
	 * @param tableName
	 * 		表名
	 *
	 * @return
	 */
	List<UserTabColumns> findProBytableName(@Param("tableName") String tableName);
}
