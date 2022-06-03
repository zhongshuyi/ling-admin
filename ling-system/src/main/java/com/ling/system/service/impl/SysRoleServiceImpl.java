package com.ling.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.common.exception.BusinessErrorException;
import com.ling.system.entity.SysRole;
import com.ling.system.mapper.SysRoleMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysMenuService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.vo.RoleVo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@Service
@RequiredArgsConstructor
@Getter
public class SysRoleServiceImpl
        extends ServicePlusImpl<SysRoleMapper, SysRole, RoleVo>
        implements ISysRoleService {

    private static final long serialVersionUID = 6794454144163365234L;

    /**
     * 菜单权限服务.
     */
    private final transient ISysMenuService sysMenuService;

    /**
     * 用户服务.
     */
    private final transient ISysAdminService sysAdminService;


    @Override
    public final void validEntityBeforeSave(final SysRole sysRole) {
        checkRoleKeyUnique(sysRole);
    }

    @Override
    public final void checkRoleKeyUnique(final SysRole roleBo) {
        final SysRole sysRole =
                getOne(Wrappers.<SysRole>lambdaQuery()
                        .eq(SysRole::getRoleKey, roleBo.getRoleKey()).last("limit 1"));
        if (sysRole != null && !sysRole.getId().equals(roleBo.getId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "角色键值已存在");
        }
    }

    @Override
    public final boolean stateChanges(
            final Long id,
            final Byte state
    ) {
        return update(Wrappers.<SysRole>lambdaUpdate()
                .eq(SysRole::getId, id).set(SysRole::getStatus, state));
    }

    @Override
    public final List<SysRole> selectRoleListByUserId(final Long userId) {
        return getBaseMapper().selectRoleListByUserId(userId);
    }

    @Override
    public final Set<Long> selectDataScope(final Long roleId) {
        return getBaseMapper().selectDataScope(roleId);
    }

    @Override
    public final Boolean setDataScope(
            final Long roleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().selectDataScope(roleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().delDataScope(roleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addDataScope(roleId, result) == result.size());
    }

    @Override
    public final Boolean setUserRole(
            final Long userId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().selectRoleIdsByUserId(userId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().delUserRole(userId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addUserRole(userId, result) == result.size());
    }

    @Override
    public final Set<Long> selectRoleIdsByUserId(final Long userId) {
        return getBaseMapper().selectRoleIdsByUserId(userId);
    }

    @Override
    public final void validEntityBeforeDel(final Long id) {
        if (CollUtil.isNotEmpty(getSysMenuService().getRolePerm(id))
                || CollUtil.isNotEmpty(getSysAdminService().selectUserListByRoleId(id))) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "此角色还有关联用户或菜单");
        }
    }
}
