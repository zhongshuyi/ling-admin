package com.mall.system.service;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
public interface IUmsAdminService extends IService<UmsAdmin> {
    /**
     * 根据角色id获取用户列表
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);
}
