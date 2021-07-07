package com.mall.service;

import com.mall.model.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@SuppressWarnings("unused")
public interface IUmsRoleService extends IService<UmsRole> {
    /**
     * 根据用户id获取角色标识列表
     * @param id 用户id
     * @return 角色标识列表
     */
    Set<String> getRoleKeyListByUserId(Long id);
}
