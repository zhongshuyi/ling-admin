package com.mall.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 公共类
 *
 * @author zsy
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
