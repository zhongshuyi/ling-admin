package com.mall.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 路由配置信息.
 *
 * @author 钟舒艺
 * @date 2021-09-17-8:55
 **/
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel("路由配置信息")
public class RouterMeta {

    @ApiModelProperty(value = "菜单排序")
    private Integer orderNo;

    @ApiModelProperty(value = "菜单名字")
    private String title;

    @ApiModelProperty(value = "是否忽略缓存(0 忽略,1 不忽略)")
    private Boolean ignoreKeepAlive;

    @ApiModelProperty(value = "是否固定标签(0不固定,1固定)")
    private Boolean affix;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "内嵌iframe的地址")
    private String frameSrc;

    @ApiModelProperty(value = "该路由切换的动画名")
    private String transitionName;

    @ApiModelProperty(value = "是否在面包屑上隐藏该路由(0隐藏,1不隐藏)")
    private Boolean hideBreadcrumb;

    @ApiModelProperty(value = "是否隐藏所有子菜单(0隐藏,1不隐藏)")
    private Boolean hideChildrenInMenu;

    @ApiModelProperty(value = "当前路由不再标签页显示 (0隐藏,1不隐藏)")
    private Boolean hideTab;

    @ApiModelProperty(value = "当前路由不再菜单显示 (0隐藏,1不隐藏)")
    private Boolean hideMenu;

    @ApiModelProperty(value = "是否是外链( 0是,1不是)")
    private Boolean isLink;
}
