package com.mall.system.service;

import com.mall.common.core.mybatisplus.core.IServicePlus;
import com.mall.system.entity.UmsRole;
import com.mall.system.vo.RoleVo;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 角色信息表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@SuppressWarnings("unused")
public interface IUmsRoleService extends IServicePlus<UmsRole, RoleVo>, Serializable {


    /**
     * 检查角色的标识是否唯一.
     *
     * @param roleBo 角色Bo
     */
    void checkRoleKeyUnique(UmsRole roleBo);

    /**
     * 更改角色状态.
     *
     * @param id    角色id
     * @param state 状态
     * @return 是否成功
     */
    boolean stateChanges(
            Long id,
            Integer state
    );

    /**
     * 根据用户id查询角色列表.
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<UmsRole> selectRoleListByUserId(Long userId);


    /**
     * 获取角色的自定义数据范围.
     *
     * @param roleId 角色id
     * @return 部门id集合
     */
    Set<Long> getDataScope(Long roleId);


    /**
     * 设置角色的自定义数据范围.
     *
     * @param roleId 角色id
     * @param newIds 新的部门id集合
     * @return 是否修改成功
     */
    Boolean setDataScope(
            Long roleId,
            Set<Long> newIds
    );

    /**
     * 设置用户的角色.
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return 是否成功
     */
    Boolean setUserRole(
            Long userId,
            Set<Long> roleIds
    );

    /**
     * 根据用户id查询角色id集合.
     *
     * @param userId 用户id
     * @return 角色id
     */
    Set<Long> selectRoleIdsByUserId(Long userId);
}
