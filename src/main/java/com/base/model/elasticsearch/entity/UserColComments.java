package com.base.model.elasticsearch.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("表注释")
public class UserColComments {
	@ApiModelProperty("表明")
	private String tableName;
	@ApiModelProperty("列名")
	private String columnName;
	@ApiModelProperty("注释")
	private String comments;
}
