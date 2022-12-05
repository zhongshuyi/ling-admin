package com.ling.system.dto;

import com.ling.common.core.validate.ValidationGroups;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 菜单操作类.
 *
 * @author 钟舒艺
 **/
@Data
public class SysMenuDTO implements Serializable {

    private static final long serialVersionUID = 8541870643648167086L;

    /**
     * 主键id.
     */
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.EDIT})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.EDIT})
    private Long id;

    /**
     * 父菜单id.
     */
    @NotNull(
            message = "父部门id不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    @Min(
            value = 0,
            message = "父部门id最低为0",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private Long parentId;

    /**
     * 权限标识.
     */
    @Size(
            max = 200,
            message = "权限标识最大200个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String perms;

    /**
     * 菜单排序.
     */
    @NotNull(
            message = "显示顺序不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    @Min(
            value = 0,
            message = "显示顺序最低为0",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private Integer orderNo;

    /**
     * 路由地址.
     */
    @Size(
            max = 300,
            message = "路由地址最大300个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String path;

    /**
     * 菜单名字.
     */
    @Size(
            max = 200,
            message = "菜单名字最大200个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    @NotNull(
            message = "菜单名称不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String title;

    /**
     * 组件路径.
     */
    @Size(
            max = 300,
            message = "菜单名字最大300个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String component;

    /**
     * 是否忽略缓存(0 忽略,1 不忽略).
     */
    private Byte ignoreKeepAlive;

    /**
     * 是否固定标签(0不固定,1固定).
     */
    private Byte affix;

    /**
     * 图标.
     */
    @Size(
            max = 300,
            message = "图标最大100个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String icon;

    /**
     * 内嵌iframe的地址.
     */
    @Size(
            max = 300,
            message = "内嵌iframe的地址最大500个字符",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String frameSrc;

    /**
     * 该路由切换的动画名.
     */
    private String transitionName;

    /**
     * 是否在面包屑上隐藏该路由(0隐藏,1不隐藏).
     */
    private Byte hideBreadcrumb;

    /**
     * 是否隐藏所有子菜单(0隐藏,1不隐藏).
     */
    private Byte hideChildrenInMenu;

    /**
     * 当前路由不再标签页显示 (0隐藏,1不隐藏).
     */
    private Byte hideTab;

    /**
     * 当前路由不再菜单显示 (0隐藏,1不隐藏).
     */
    private Byte hideMenu;

    /**
     * 是否是外链( 0是,1不是).
     */
    private Byte isLink;

    /**
     * 菜单类型( 0 目录,1 菜单,2 按钮).
     */
    private Byte menuType;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 状态(0启用 1禁用).
     */
    private Byte status;

    /**
     * 是否是内联 0是,1不是.
     */
    private Byte isFrame;
}
