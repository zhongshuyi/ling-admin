package com.mall.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 钟舒艺
 * @date 2021-11-06-16:58
 **/
@Data
public class BasePageQuery {

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", example = "10")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数", example = "1")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty(value = "排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向")
    private String isAsc;

}