package com.mall.system.service;

import com.mall.system.entity.UmsRole;
import com.mall.common.core.mybatisplus.core.IServicePlus;
import com.mall.system.vo.RoleVo;
import java.io.Serializable;

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
    boolean stateChanges(Long id, Integer state);
}
