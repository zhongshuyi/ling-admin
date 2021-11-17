package com.mall.system.service;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.common.core.mybatisplus.core.IServicePlus;
import com.mall.system.vo.UserVo;
import java.util.List;

/**
 * 用户信息表 服务类
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
public interface IUmsAdminService extends IServicePlus<UmsAdmin, UserVo> {
    /**
     * 根据角色id获取用户列表
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);
}
