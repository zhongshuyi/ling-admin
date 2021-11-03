package com.mall.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 公共类
 * @author zsy
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索值
	 */
	@ApiModelProperty(value = "搜索值")
	@JsonIgnore
	@TableField(exist = false)
	private String searchValue;

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	@JsonIgnore
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonIgnore
	private Date createTime;

	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "更新者")
	@JsonIgnore
	private String updateBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@JsonIgnore
	private Date updateTime;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@JsonIgnore
	private String remark;

	/**
	 * 请求参数
	 */
	@ApiModelProperty(value = "请求参数")
	@JsonIgnore
	@TableField(exist = false)
	private Map<String, Object> params = new HashMap<>();

}
