package com.mall.system.service.impl;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.system.mapper.UmsAdminMapper;
import com.mall.system.service.IUmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 用户信息表 服务实现类
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Override
    public List<UmsAdmin> getUserListByRoleId(Long roleId) {
        return baseMapper.getUserListByRoleId(roleId);
    }
}
