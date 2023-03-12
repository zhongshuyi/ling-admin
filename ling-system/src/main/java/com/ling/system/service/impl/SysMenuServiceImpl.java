package com.ling.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.common.constant.AppConstants;
import com.ling.common.core.domain.model.SysMenu;
import com.ling.common.enums.MenuType;
import com.ling.system.convert.SysMenuConvert;
import com.ling.system.dto.SysMenuDTO;
import com.ling.system.mapper.SysMenuMapper;
import com.ling.system.service.ISysMenuService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
     * 获取所有菜单列表.
     *
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> listMenuAll() {
        return list(
                Wrappers.<SysMenu>lambdaQuery().orderByAsc(Arrays.asList(SysMenu::getOrderNo, SysMenu::getParentId)));
    }

    @Override
    public final List<SysMenu> listRouterAll() {
        return list(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getStatus, AppConstants.ENABLE)
                .in(SysMenu::getMenuType, MenuType.DIRECTORY.getCode(), MenuType.MENU.getCode())
                .orderByAsc(Arrays.asList(SysMenu::getOrderNo, SysMenu::getParentId)));
    }

    @Override
    public final List<SysMenu> listMenuByIds(final List<Long> permIds) {
        return list(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, permIds)
                .in(SysMenu::getMenuType, MenuType.DIRECTORY.getCode(), MenuType.MENU.getCode())
                .eq(SysMenu::getStatus, AppConstants.ENABLE));
    }

    @Override
    public final Set<Long> listRolePermsId(final Long userId) {
        return baseMapper.listUserPermsIdsById(userId);
    }

    @Override
    public final Set<Long> listDeptPermsId(final Long userId) {
        return baseMapper.listDeptRolePermsIdsByUserId(userId);
    }

    @Override
    public final Boolean saveMenuByDTO(final SysMenuDTO menuDTO) {
        final SysMenu menu = SysMenuConvert.INSTANCT.dtoToEntity(menuDTO);
        return save(menu);
    }

    @Override
    public final Boolean removeMenuById(final Long id) {
        final List<SysMenu> list = listMenuChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (final SysMenu menu : list) {
                removeMenuById(menu.getId());
            }
        }
        getBaseMapper().deleteDeptPermByPermId(id);
        getBaseMapper().deleteRolePermByPermId(id);
        getBaseMapper().deleteDeptRolePermByPermId(id);
        return removeById(id);
    }

    @Override
    public final Boolean checkMenuUnique(final SysMenu bo) {
        return !this.list(Wrappers.<SysMenu>lambdaQuery().ne(SysMenu::getId, bo.getId()).eq(SysMenu::getParentId,
                bo.getParentId()
        ).and(q -> q.eq(SysMenu::getPath, bo.getPath()).or().eq(SysMenu::getTitle, bo.getTitle()))).isEmpty();
    }

    @Override
    public final List<SysMenu> listMenuChildren(final Long id) {
        return this.list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, id));
    }

    @Override
    public final List<SysMenu> listDeptPermMenu(final Long deptId) {
        return getBaseMapper().listDeptPermMenu(deptId);
    }

    @Override
    public final Set<Long> listDeptPerm(final Long id) {
        return getBaseMapper().listDeptPerm(id);
    }

    @Override
    public final Boolean updateDeptPerm(
            final Long deptId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().listDeptPerm(deptId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().deleteDeptPermByDeptId(
                deptId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().insertDeptPermByDeptId(deptId, result) == result.size());
    }

    @Override
    public final Set<Long> listRolePerm(final Long id) {
        return getBaseMapper().listRolePermIds(id);
    }

    @Override
    public final Boolean updateRolePerm(
            final Long roleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = getBaseMapper().listRolePermIds(roleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().deleteRolePermByRoleId(
                roleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().insertRolePermByRoleId(roleId, result) == result.size());
    }

    @Override
    public final Set<Long> listDeptRolePermIds(final Long deptRoleId) {
        return getBaseMapper().listDeptRolePermIds(deptRoleId);
    }

    @Override
    public final Boolean updateDeptRolePerm(
            final Long deptRoleId,
            final Set<Long> newIds
    ) {
        final Set<Long> oldIds = listDeptRolePermIds(deptRoleId);
        final Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final boolean isSuccess = result.isEmpty() || getBaseMapper().deleteDeptRolePerm(
                deptRoleId, result) == result.size();
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return isSuccess && (result.isEmpty() || getBaseMapper().insertRolePermByRoleId(
                deptRoleId, result) == result.size());
    }
}
