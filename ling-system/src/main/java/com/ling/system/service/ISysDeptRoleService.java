package com.ling.system.service;

import com.ling.common.core.mybatisplus.core.IServicePlus;
import com.ling.system.entity.SysDeptRole;
import com.ling.system.vo.DeptRoleVo;
import java.util.Set;

/**
 * 上级管理负责的部门服务.
 *
 * @author 钟舒艺
 **/
public interface ISysDeptRoleService extends IServicePlus<SysDeptRole, DeptRoleVo> {

    /**
     * 根据用户id获取该用户拥有的部门角色id.
     *
     * @param deptId 部门id
     * @param userId 用户id
     * @return 部门角色id
     */
    Set<Long> getDeptRoleIdsByUserId(
            Long deptId,
            Long userId);

    /**
     * 设置用户的部门角色.
     *
     * @param deptId      部门id
     * @param userId      用户id
     * @param deptRoleIds 部门角色id集合
     * @return 是否成功
     */
    Boolean setDeptRoleToUser(
            Long deptId,
            Long userId,
            Set<Long> deptRoleIds);
}
