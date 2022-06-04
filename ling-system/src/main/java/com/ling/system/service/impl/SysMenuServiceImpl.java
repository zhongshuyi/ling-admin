package com.ling.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.common.constant.AppConstants;
import com.ling.common.enums.MenuType;
import com.ling.system.dto.MenuDTO;
import com.ling.system.entity.SysMenu;
import com.ling.system.entity.SysPermissionUrl;
import com.ling.system.mapper.SysMenuMapper;
import com.ling.system.mapper.SysPermissionUrlMapper;
import com.ling.system.service.ISysMenuService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 菜单表 服务实现类.
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private static final long serialVersionUID = -5579437013223054201L;

    /**
     * 权限与url.
     */
    @Getter
    private final transient SysPermissionUrlMapper sysPermissionUrlMapper;


    /**
     * 获取所有菜单列表.
     *
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuListAll() {
        return list(
                Wrappers.<SysMenu>lambdaQuery().orderByAsc(Arrays.asList(SysMenu::getOrderNo, SysMenu::getParentId)));
    }

    @Override
    public final List<SysMenu> selectRouterListAll() {
        return list(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getStatus, AppConstants.ENABLE)
                .in(SysMenu::getMenuType, MenuType.DIRECTORY.getCode(), MenuType.MENU.getCode())
                .orderByAsc(Arrays.asList(SysMenu::getOrderNo, SysMenu::getParentId)));
    }

    @Override
    public final List<SysMenu> selectMenuByIds(final List<Long> permIds) {
        return list(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, permIds)
                .in(SysMenu::getMenuType, MenuType.DIRECTORY.getCode(), MenuType.MENU.getCode())
                .eq(SysMenu::getStatus, AppConstants.ENABLE));
    }

    @Override
    public final Set<Long> selectRolePermsId(final Long userId) {
        return baseMapper.selectUserPermsIdsById(userId);
    }

    @Override
    public final Set<Long> selectDeptPermsId(final Long userId) {
        return baseMapper.selectDeptRolePermsIdsByUserId(userId);
    }

    @Override
    public final Boolean addByDTO(final MenuDTO menuDTO) {
        final SysMenu menu = BeanUtil.toBean(menuDTO, SysMenu.class);
        save(menu);
        if (
                menuDTO.getMenuType().equals(MenuType.BUTTON.getCode())
                        &&
                        CollUtil.isNotEmpty(menuDTO.getPermissionUrl())
        ) {
            for (final SysPermissionUrl url : menuDTO.getPermissionUrl()) {
                url.setMenuId(menu.getId());
            }
            getSysPermissionUrlMapper().insertAll(menuDTO.getPermissionUrl());
        }
        return true;
    }

    @Override
    public final Boolean deleteById(final Long id) {
        final List<SysMenu> list = selectMenuChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (final SysMenu menu : list) {
                deleteById(menu.getId());
            }
        }
        getBaseMapper().delDeptPermByPermId(id);
        getBaseMapper().delRolePermByPermId(id);
        getBaseMapper().delDeptRolePermByPermId(id);
        getSysPermissionUrlMapper().delete(
                Wrappers.<SysPermissionUrl>lambdaQuery().eq(SysPermissionUrl::getMenuId, id));
        return removeById(id);
    }

    @Override
    public final Boolean checkMenuUnique(final SysMenu bo) {
        return !this.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getId, bo.getId()).eq(SysMenu::getParentId,
                bo.getParentId()
        ).and(q -> q.eq(SysMenu::getPath, bo.getPath()).or().eq(SysMenu::getTitle, bo.getTitle()))).isEmpty();
    }

    @Override
    public final List<SysMenu> selectMenuChildren(final Long id) {
        return this.list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, id));
    }

    @Override
    public final List<SysMenu> selectDeptPermMenu(final Long deptId) {
        return getBaseMapper().selectDeptPermMenu(deptId);
    }

    @Override
    public final Set<Long> selectDeptPerm(final Long id) {
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
        final boolean isSuccess = result.isEmpty() || getBaseMapper().delDeptPermByDeptId(
                deptId, result) == result.size();
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
        final boolean isSuccess = result.isEmpty() || getBaseMapper().delRolePermByRoleId(
                roleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addRolePermByRoleId(roleId, result) == result.size());
    }

    @Override
    public final Set<Long> selectDeptRolePermIds(final Long deptRoleId) {
        return getBaseMapper().selectDeptRolePerm(deptRoleId);
    }

    @Override
    public final Boolean setDeptRolePerm(
            final Long deptRoleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = selectDeptRolePermIds(deptRoleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().delDeptRolePerm(
                deptRoleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().addRolePermByRoleId(
                deptRoleId, result) == result.size());
    }
}
