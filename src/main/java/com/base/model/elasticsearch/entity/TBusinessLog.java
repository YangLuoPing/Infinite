package com.base.model.elasticsearch.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 表对应的类型
 * @Author:
 * @Date: 2020-05-04
 * @Version: V1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("es错误日志表")
public class TBusinessLog {

	/**
	 * id
	 */
	// 主键
	//@TableId(type = IdType.AUTO)
	private String id;
	/**
	 * 日志信息
	 */
	private String logInfo;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 业务模块
	 */
	private String businessType;
}
