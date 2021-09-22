package com.mall.system.bo.add;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单表
 *
 * @author 钟舒艺
 * @since 2021-09-22
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "菜单添加" , description = "菜单的添加对象及规则" )
public class MenuAddBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id" )
    private Long id;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id" )
    private Long parentId;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识" )
    private String perms;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序" )
    private Integer orderNo;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址" )
    private String path;

    /**
     * 菜单名字
     */
    @ApiModelProperty(value = "菜单名字" )
    private String title;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径" )
    private String component;

    /**
     * 是否忽略缓存(0 忽略,1 不忽略)
     */
    @ApiModelProperty(value = "是否忽略缓存(0 忽略,1 不忽略)" )
    private Integer ignoreKeepAlive;

    /**
     * 是否固定标签(0不固定,1固定)
     */
    @ApiModelProperty(value = "是否固定标签(0不固定,1固定)" )
    private Integer affix;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标" )
    private String icon;

    /**
     * 内嵌iframe的地址
     */
    @ApiModelProperty(value = "内嵌iframe的地址" )
    private String frameSrc;

    /**
     * 该路由切换的动画名
     */
    @ApiModelProperty(value = "该路由切换的动画名" )
    private String transitionName;

    /**
     * 是否在面包屑上隐藏该路由(0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "是否在面包屑上隐藏该路由(0隐藏,1不隐藏)" )
    private Integer hideBreadcrumb;

    /**
     * 是否隐藏所有子菜单(0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "是否隐藏所有子菜单(0隐藏,1不隐藏)" )
    private Integer hideChildrenInMenu;

    /**
     * 当前路由不再标签页显示 (0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "当前路由不再标签页显示 (0隐藏,1不隐藏)" )
    private Integer hideTab;

    /**
     * 当前路由不再菜单显示 (0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "当前路由不再菜单显示 (0隐藏,1不隐藏)" )
    private Integer hideMenu;

    /**
     * 是否是外链( 0是,1不是)
     */
    @ApiModelProperty(value = "是否是外链( 0是,1不是)" )
    private Integer isLink;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者" )
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间" )
    private Date createTime;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者" )
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间" )
    private Date updateTime;

    /**
     * 菜单类型( 0 目录,1 菜单,2 按钮)
     */
    @ApiModelProperty(value = "菜单类型( 0目录,1菜单,2按钮)" )
    private Integer menuType;

    @ApiModelProperty(value = "备注" )
    private String remark;

    /**
     * 状态(0启用 1禁用)
     */
    @ApiModelProperty(value = "状态(0启用 1禁用)" )
    private Integer status;

    /**
     * 是否是外链 0是,1不是
     */
    @ApiModelProperty(value = "是否是外链 0是,1不是" )
    private Integer isFrame;


}