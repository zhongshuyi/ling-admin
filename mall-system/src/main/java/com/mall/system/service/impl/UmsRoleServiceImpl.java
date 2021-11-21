package com.mall.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.entity.UmsRole;
import com.mall.system.mapper.UmsRoleMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UmsRoleServiceImpl
        extends ServicePlusImpl<UmsRoleMapper, UmsRole, RoleVo>
        implements IUmsRoleService {

    private static final long serialVersionUID = 6794454144163365234L;

    /**
     * 菜单权限服务.
     */
    private final transient IUmsMenuService umsMenuService;

    /**
     * 用户服务.
     */
    private final transient IUmsAdminService umsAdminService;


    @Override
    public final void validEntityBeforeSave(final UmsRole umsRole) {
        checkRoleKeyUnique(umsRole);
    }

    @Override
    public final void checkRoleKeyUnique(final UmsRole roleBo) {
        UmsRole umsRole =
                getOne(Wrappers.<UmsRole>lambdaQuery()
                        .eq(UmsRole::getRoleKey, roleBo.getRoleKey()).last("limit 1"));
        if (umsRole != null && !umsRole.getId().equals(roleBo.getId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "角色键值已存在");
        }
    }

    @Override
    public final boolean stateChanges(final Long id, final Integer state) {
        return update(Wrappers.<UmsRole>lambdaUpdate()
                .eq(UmsRole::getId, id).set(UmsRole::getStatus, state));
    }

    @Override
    public final List<UmsRole> selectRoleListByUserId(final Long userId) {
        return getBaseMapper().selectRoleListByUserId(userId);
    }

    @Override
    public final void validEntityBeforeDel(final Long id) {
        if (CollUtil.isNotEmpty(umsMenuService.getRolePerm(id))
                || CollUtil.isNotEmpty(umsAdminService.getUserListByRoleId(id))) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "此角色还有关联用户或菜单");
        }
    }
}
