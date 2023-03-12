package com.ling.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2023-03-09 15:51
 **/
@Getter
@AllArgsConstructor
public enum PublicPermissionEnum {
    /**
     * 查询列表.
     */
    LIST("list", "查询列表"),
    /**
     * 查询详细信息.
     */
    QUERY("query", "查询详细信息"),
    /**
     * 新增记录.
     */
    ADD("add", "新增记录"),
    /**
     * 修改记录.
     */
    EDIT("edit", "修改记录"),
    /**
     * 删除单条记录.
     */
    DELETE("delete", "删除单条记录");
    /**
     * 权限字符串.
     */
    private final String code;

    /**
     * 解释.
     */
    private final String comment;
}
