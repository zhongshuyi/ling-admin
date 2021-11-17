package com.mall.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.mapper.UmsAdminMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息表 服务实现类
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@Service
public class UmsAdminServiceImpl extends ServicePlusImpl<UmsAdminMapper, UmsAdmin, UserVo> implements IUmsAdminService {

    private static final long serialVersionUID = -6767396404407827925L;

    @Override
    public List<UmsAdmin> getUserListByRoleId(Long roleId) {
        return baseMapper.getUserListByRoleId(roleId);
    }

    @Override
    public void validEntityBeforeSave(UmsAdmin umsAdmin) {
        super.validEntityBeforeSave(umsAdmin);
        if (!checkUserNameUnique(umsAdmin)) {
            throw new BusinessErrorException(400, "用户名已存在");
        }
    }

    /**
     * 检查用户名是否唯一
     *
     * @param umsAdmin 用户信息
     * @return true就是唯一的
     */
    public Boolean checkUserNameUnique(UmsAdmin umsAdmin) {
        UmsAdmin user = getOne(Wrappers.<UmsAdmin>lambdaQuery().eq(UmsAdmin::getUsername, umsAdmin.getUsername()).last("limit 1"));
        return user != null && !umsAdmin.getId().equals(user.getId());
    }
}
