package com.mall.system.bo.query;

import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单查询
 *
 * @author 钟舒艺
 * @date 2021-09-21-14:28
 **/
public class MenuQueryBo {

    /**
     * 菜单名字
     */
    @ApiModelProperty(value = "菜单名字" )
    private String title;


    /**
     * 菜单状态
     */
    @ApiModelProperty(value = "菜单状态" )
    private Integer status;
}
