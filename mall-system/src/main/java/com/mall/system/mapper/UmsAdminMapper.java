package com.mall.system.mapper;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户信息表 Mapper 接口
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    /**
     * 根据角色id获取用户列表
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);
}
