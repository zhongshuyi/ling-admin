package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.system.bo.MenuBo;
import com.mall.system.entity.UmsMenu;
import com.mall.system.entity.UmsPermissionUrl;
import com.mall.system.mapper.UmsMenuMapper;
import com.mall.system.mapper.UmsPermissionUrlMapper;
import com.mall.system.service.IUmsMenuService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 菜单表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
@Service
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class UmsMenuServiceImpl
        extends ServiceImpl<UmsMenuMapper, UmsMenu>
        implements IUmsMenuService {

    private static final long serialVersionUID = -5579437013223054201L;

    /**
     * 权限与url.
     */
    private final transient UmsPermissionUrlMapper umsPermissionUrlMapper;

    /**
     * 获取所有菜单列表.
     *
     * @return 菜单列表
     */
    @Override
    public List<UmsMenu> selectMenuListAll() {
        return list(Wrappers
                .<UmsMenu>lambdaQuery()
                .orderByAsc(Arrays.asList(UmsMenu::getOrderNo, UmsMenu::getParentId)));
    }

    @Override
    public final List<UmsMenu> selectRouterListAll() {
        return list(Wrappers
                .<UmsMenu>lambdaQuery()
                .eq(UmsMenu::getStatus, 0)
                .in(UmsMenu::getMenuType, 0, 1)
                .orderByAsc(Arrays.asList(UmsMenu::getOrderNo, UmsMenu::getParentId)));
    }

    @Override
    public final List<UmsMenu> selectMenuByIds(final List<Long> permIds) {
        return list(
                Wrappers.<UmsMenu>lambdaQuery()
                        .in(UmsMenu::getId, permIds)
                        .in(UmsMenu::getMenuType, 0L, 1L)
                        .eq(UmsMenu::getStatus, 0L));
    }

    @Override
    public final Set<Long> selectRolePermsId(final Long userId) {
        return baseMapper.selectUserPermsIdsById(userId);
    }

    @Override
    public final Set<Long> selectDeptPermsId(final Long userId) {
        return baseMapper.selectDeptPermsId(userId);
    }

    @Override
    public final Boolean addByAddBo(final MenuBo menuBo) {
        final UmsMenu menu = BeanUtil.toBean(menuBo, UmsMenu.class);
        save(menu);
        for (final UmsPermissionUrl url : menuBo.getPermissionUrl()) {
            url.setMenuId(menu.getId());
        }
        umsPermissionUrlMapper.insertAll(menuBo.getPermissionUrl());
        return true;
    }

    @Override
    public final Boolean deleteById(final Long id) {
        final List<UmsMenu> list = getMenuChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (final UmsMenu menu : list) {
                deleteById(menu.getId());
            }
        }
        getBaseMapper().delDeptPermByPermId(id);
        getBaseMapper().delRolePermByPermId(id);
        getBaseMapper().delDeptRolePermByPermId(id);
        return removeById(id);
    }

    @Override
    public final Boolean checkMenuUnique(final UmsMenu bo) {
        return !this.list(
                        Wrappers.<UmsMenu>lambdaQuery()
                                .ne(UmsMenu::getId, bo.getId())
                                .eq(UmsMenu::getParentId, bo.getParentId())
                                .and(q -> q
                                        .eq(UmsMenu::getPath, bo.getPath()).or()
                                        .eq(UmsMenu::getTitle, bo.getTitle())))
                .isEmpty();
    }

    @Override
    public final List<UmsMenu> getMenuChildren(final Long id) {
        return this.list(Wrappers.<UmsMenu>lambdaQuery().eq(UmsMenu::getParentId, id));
    }

    @Override
    public final Set<Long> getDeptPerm(final Long id) {
        return getBaseMapper().selectDeptPerm(id);
    }

    @Override
    public final Boolean setDeptPerm(
            final Long deptId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().selectDeptPerm(deptId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess =
                result.isEmpty() || getBaseMapper().delDeptPermByDeptId(deptId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addDeptPermByDeptId(deptId, result) == result.size());
    }

    @Override
    public final Set<Long> getRolePerm(final Long id) {
        return getBaseMapper().selectRolePerm(id);
    }

    @Override
    public final Boolean setRolePerm(
            final Long roleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().selectRolePerm(roleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess =
                result.isEmpty() || getBaseMapper().delRolePermByRoleId(roleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addRolePermByRoleId(roleId, result) == result.size());
    }

    @Override
    public final Set<Long> getDeptRolePerm(final Long deptRoleId) {
        return getBaseMapper().selectDeptRolePerm();
    }

    @Override
    public final Boolean setDeptRolePerm(
            final Long deptRoleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getDeptRolePerm(deptRoleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess =
                result.isEmpty() || getBaseMapper().delDeptRolePerm(deptRoleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addRolePermByRoleId(
                deptRoleId, result) == result.size());
    }
}
