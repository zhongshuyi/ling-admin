package com.ling.common.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限枚举.
 *
 * @author 钟舒艺
 **/
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum {

    /**
     * 全部数据权限.
     */
    ALL(
            1,
            "全部数据权限",
            ""
    ),
    /**
     * 自定数据权限.
     */
    CUSTOM(
            2,
            "自定数据权限",
            "{deptIdColumnName} in "
                    + "(SELECT dept_id FROM sys_role_dept where role_id in({roleIds})) or {userIdColumnName} = {userId}"
    ),
    /**
     * 本部门数据权限.
     */
    DEPT(
            3,
            "本部门数据权限",
            "{deptIdColumnName} in ({deptIds}) or user_id = {userId}"
    ),
    /**
     * 本部门及下级部门数据权限.
     */
    DEPT_AND_SUB(
            4,
            "本部门及下级部门数据权限",
            "{deptIdColumnName} in ("
                    + "SELECT id from sys_dept where parent_list regexp concat("
                    + "(SELECT group_concat(parent_list separator '|') from sys_dept where id in ({deptIds}))))"
    ),
    /**
     * 仅本人数据权限.
     */
    ME(
            5,
            "仅本人数据权限",
            "{userIdColumnName} = {userId}"
    );
    /**
     * 代码.
     */
    private final Integer code;
    /**
     * 备注.
     */
    private final String remark;

    /**
     * sql条件模板.
     */
    private final String template;

    /**
     * 构建数据权限所需要的条件.
     *
     * @param userId           用户id
     * @param roleIds          角色id集合
     * @param deptIds          部门id集合
     * @param userIdColumnName 用户标识列名
     * @param deptIdColumnName 部门标识列名
     * @return 构建好的条件
     */
    public String buildTemplate(
            final Long userId,
            final List<Long> roleIds,
            final List<Long> deptIds,
            final String userIdColumnName,
            final String deptIdColumnName
    ) {
        return StrUtil.format(
                this.template,
                MapUtil.builder(new HashMap<String, String>())
                        .put("userId", userId.toString())
                        .put("roleIds", CollUtil.join(roleIds, ","))
                        .put("deptIds", CollUtil.join(deptIds, ","))
                        .put("userIdColumnName", userIdColumnName)
                        .put("deptIdColumnName", deptIdColumnName)
                        .build());
    }
}
