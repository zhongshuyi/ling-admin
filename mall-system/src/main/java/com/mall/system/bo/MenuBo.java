package com.mall.system.bo;

import com.mall.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 钟舒艺
 * @date 2021-11-07-14:59
 **/
@Data
public class MenuBo implements Serializable {

    private static final long serialVersionUID = 8541870643648167086L;

    /**
     * 主键id
     */
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id")
    @NotNull(message = "父部门id不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    @Min(value = 0, message = "父部门id最低为0", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private Long parentId;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    @Size(max = 200 ,message = "权限标识最大200个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String perms;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序")
    @NotNull(message = "显示顺序不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    @Min(value = 0, message = "显示顺序最低为0", groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    private Integer orderNo;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    @Size(max = 300 ,message = "路由地址最大300个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String path;

    /**
     * 菜单名字
     */
    @ApiModelProperty(value = "菜单名字")
    @Size(max = 200 ,message = "菜单名字最大200个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    @NotNull(message = "菜单名称不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String title;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径")
    @Size(max = 300 ,message = "菜单名字最大300个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String component;

    /**
     * 是否忽略缓存(0 忽略,1 不忽略)
     */
    @ApiModelProperty(value = "是否忽略缓存(0 忽略,1 不忽略)")
    private Integer ignoreKeepAlive = 1;

    /**
     * 是否固定标签(0不固定,1固定)
     */
    @ApiModelProperty(value = "是否固定标签(0不固定,1固定)")
    private Integer affix = 0;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    @Size(max = 300 ,message = "图标最大100个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String icon;

    /**
     * 内嵌iframe的地址
     */
    @ApiModelProperty(value = "内嵌iframe的地址")
    @Size(max = 300 ,message = "内嵌iframe的地址最大500个字符", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String frameSrc;

    /**
     * 该路由切换的动画名
     */
    @ApiModelProperty(value = "该路由切换的动画名")
    private String transitionName;

    /**
     * 是否在面包屑上隐藏该路由(0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "是否在面包屑上隐藏该路由(0隐藏,1不隐藏)")
    private Integer hideBreadcrumb = 1;

    /**
     * 是否隐藏所有子菜单(0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "是否隐藏所有子菜单(0隐藏,1不隐藏)")
    private Integer hideChildrenInMenu = 1;

    /**
     * 当前路由不再标签页显示 (0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "当前路由不再标签页显示 (0隐藏,1不隐藏)")
    private Integer hideTab = 1;

    /**
     * 当前路由不再菜单显示 (0隐藏,1不隐藏)
     */
    @ApiModelProperty(value = "当前路由不再菜单显示 (0隐藏,1不隐藏)")
    private Integer hideMenu = 1;

    /**
     * 是否是外链( 0是,1不是)
     */
    @ApiModelProperty(value = "是否是外链( 0是,1不是)")
    private Integer isLink = 1;

    /**
     * 菜单类型( 0 目录,1 菜单,2 按钮)
     */
    @ApiModelProperty(value = "菜单类型( 0目录,1菜单,2按钮)")
    private Integer menuType = 1;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态(0启用 1禁用)
     */
    @ApiModelProperty(value = "状态(0启用 1禁用)")
    private Integer status = 0;

    /**
     * 是否是内联 0是,1不是
     */
    @ApiModelProperty(value = "是否是外链 0是,1不是")
    private Integer isFrame = 0;

}
