package com.ling.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ling.common.core.domain.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 菜单表.
 *
 * @author 钟舒艺
 * @since 2021-09-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单id.
     */
    private Long parentId;

    /**
     * 权限标识.
     */
    private String perms;

    /**
     * 菜单排序.
     */
    private Integer orderNo;

    /**
     * 路由地址.
     */
    private String path;

    /**
     * 菜单名字.
     */
    private String title;

    /**
     * 组件路径.
     */
    private String component;

    /**
     * 是否忽略缓存(1 忽略,0 不忽略).
     */
    private Byte ignoreKeepAlive;

    /**
     * 是否固定标签(0不固定,1固定).
     */
    private Byte affix;

    /**
     * 图标.
     */
    private String icon;

    /**
     * 内嵌iframe的地址.
     */
    private String frameSrc;

    /**
     * 该路由切换的动画名.
     */
    private String transitionName;

    /**
     * 是否在面包屑上隐藏该路由(1隐藏,0不隐藏).
     */
    private Byte hideBreadcrumb;

    /**
     * 是否隐藏所有子菜单(1隐藏,0不隐藏).
     */
    private Byte hideChildrenInMenu;

    /**
     * 当前路由不再标签页显示 (1隐藏,0不隐藏).
     */
    private Byte hideTab;

    /**
     * 当前路由不再菜单显示 (1隐藏,0不隐藏).
     */
    private Byte hideMenu;

    /**
     * 是否是外链( 1是,0不是).
     */
    private Byte isLink;

    /**
     * 菜单类型( 0 目录,1 菜单,2 按钮).
     */
    private Byte menuType;

    /**
     * 状态(1启用 0禁用).
     */
    private Byte status;

    /**
     * 是否是外链 1是,0不是.
     */
    private Byte isFrame;


}
