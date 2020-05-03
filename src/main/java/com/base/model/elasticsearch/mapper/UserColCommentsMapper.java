package com.base.model.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.model.elasticsearch.entity.UserColComments;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserColCommentsMapper extends BaseMapper<UserColComments> {
	/**
	 * 查询表的列
	 *
	 * @return
	 */
	@Select("SELECT wm_concat(column_name)  columnName,tableName FROM user_col_comments")
	List<UserColComments> findAll();

	/**
	 * 查询表的列
	 *
	 * @param tableName
	 * 		表明
	 *
	 * @return
	 */
	@Select("SELECT SELECT wm_concat(column_name)  column_name,table_name FROM user_col_comments WHERE table_name =#{tableName}")
	List<UserColComments> findBytableName(@Param("tableName") String tableName);
}
