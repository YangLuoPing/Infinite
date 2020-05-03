package com.base.model.elasticsearch.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("表数据数据类型")
public class UserTabColumns {
	@ApiModelProperty("表明")
	private String tableName;
	@ApiModelProperty("列名")
	private String columnName;
	@ApiModelProperty("数据类型")
	private String dataType;
	@ApiModelProperty("数据类型长度")
	private Integer dataLength;
}
