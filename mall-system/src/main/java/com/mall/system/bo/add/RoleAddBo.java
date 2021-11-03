package com.mall.system.bo.add;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 钟舒艺
 * @date 2021-10-26-14:26
 **/
@Data
public class RoleAddBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer orderNo;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;


    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
