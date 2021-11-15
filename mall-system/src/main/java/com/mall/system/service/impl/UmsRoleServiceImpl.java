package com.mall.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.domain.entity.UmsRole;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.mapper.UmsRoleMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务实现类
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UmsRoleServiceImpl extends ServicePlusImpl<UmsRoleMapper, UmsRole, RoleVo> implements IUmsRoleService {

    private final IUmsMenuService umsMenuService;

    private final IUmsAdminService umsAdminService;

    @Override
    public void validEntityBeforeSave(UmsRole umsRole) {
        checkRoleKeyUnique(umsRole);
    }

    @Override
    public void checkRoleKeyUnique(UmsRole roleBo) {
        UmsRole umsRole = getOne(Wrappers.<UmsRole>lambdaQuery().eq(UmsRole::getRoleKey, roleBo.getRoleKey()).last("limit 1"));
        if (umsRole != null && !umsRole.getId().equals(roleBo.getId())) {
            throw new BusinessErrorException(400, "角色键值已存在");
        }
    }

    @Override
    public boolean stateChanges(Long id, Integer state) {
        return update(Wrappers.<UmsRole>lambdaUpdate().eq(UmsRole::getId, id).set(UmsRole::getStatus, state));
    }

    @Override
    public void validEntityBeforeDel(Long id) {
        if (CollUtil.isNotEmpty(umsMenuService.getRolePerm(id)) || CollUtil.isNotEmpty(umsAdminService.getUserListByRoleId(id))) {
            throw new BusinessErrorException(400, "此角色还有关联用户或菜单");
        }
    }
}
