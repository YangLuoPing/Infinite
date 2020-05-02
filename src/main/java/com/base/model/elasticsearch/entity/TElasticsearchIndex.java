package com.base.model.elasticsearch.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data //使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去
@AllArgsConstructor //使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor //使用后创建一个无参构造函数
@ApiModel("Elasticsearch的Index存储表")
public class TElasticsearchIndex implements Serializable {
    private static final long serialVersionUID = -47936328970886524L;
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String indexname;
    @ApiModelProperty("数据库名称")
    private  String dataname;
    /**
     * 元数据
     */
    @ApiModelProperty("元数据")
    private String type;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标志
     */
    private Integer deleted;

}