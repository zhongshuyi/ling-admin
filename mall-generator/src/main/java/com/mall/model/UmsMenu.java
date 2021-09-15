package com.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单表
 * @author 钟舒艺
 * @since 2021-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsMenu对象", description="菜单表")
public class UmsMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父菜单id")
    private Long parentId;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "是否是外链 0是,1不是")
    private Integer isFrame;

    @ApiModelProperty(value = "菜单类型( 0目录,1菜单,2按钮)")
    private Integer menuType;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单排序")
    private Integer orderNo;

    @ApiModelProperty(value = "菜单名字")
    private String title;

    @ApiModelProperty(value = "是否忽略缓存(0 忽略,1 不忽略)")
    private Integer ignoreKeepAlive;

    @ApiModelProperty(value = "是否固定标签(0不固定,1固定)")
    private Integer affix;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "内嵌iframe的地址")
    private String frameSrc;

    @ApiModelProperty(value = "该路由切换的动画名")
    private String transitionName;

    @ApiModelProperty(value = "是否在面包屑上隐藏该路由(0隐藏,1不隐藏)")
    private Integer hideBreadcrumb;

    @ApiModelProperty(value = "是否隐藏所有子菜单(0隐藏,1不隐藏)")
    private Integer hideChildrenInMenu;

    @ApiModelProperty(value = "当前路由不再标签页显示 (0隐藏,1不隐藏)")
    private Integer hideTab;

    @ApiModelProperty(value = "当前路由不再菜单显示 (0隐藏,1不隐藏)")
    private Integer hideMenu;

    @ApiModelProperty(value = "是否是外链( 0是,1不是)")
    private Integer isLink;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单状态")
    private Integer status;


}
