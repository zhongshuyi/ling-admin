package com.ling.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 路由配置信息.
 *
 * @author 钟舒艺
 **/
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class RouterMeta {

    /**
     * 菜单排序.
     */
    private Integer orderNo;

    /**
     * 菜单名字.
     */
    private String title;

    /**
     * 是否忽略缓存(0 忽略,1 不忽略).
     */
    private Boolean ignoreKeepAlive;

    /**
     * 是否固定标签(0不固定,1固定).
     */
    private Boolean affix;

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
     * 是否在面包屑上隐藏该路由(0隐藏,1不隐藏).
     */
    private Boolean hideBreadcrumb;

    /**
     * 是否隐藏所有子菜单(0隐藏,1不隐藏).
     */
    private Boolean hideChildrenInMenu;

    /**
     * 当前路由不再标签页显示 (0隐藏,1不隐藏).
     */
    private Boolean hideTab;

    /**
     * 当前路由不再菜单显示 (0隐藏,1不隐藏).
     */
    private Boolean hideMenu;

    /**
     * 是否是外链( 0是,1不是).
     */
    private Boolean isLink;
}
