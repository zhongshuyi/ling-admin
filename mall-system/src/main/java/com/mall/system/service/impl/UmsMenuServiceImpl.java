package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.mapper.UmsMenuMapper;
import com.mall.system.service.IUmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 菜单表 服务实现类
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    /**
     * 获取所有菜单列表
     *
     * @return 菜单列表
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<UmsMenu> selectMenuListAll() {
        return list(Wrappers
                .<UmsMenu>lambdaQuery()
                .orderByAsc(UmsMenu::getParentId, UmsMenu::getOrderNo));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UmsMenu> selectRouterListAll() {
        return list(Wrappers
                .<UmsMenu>lambdaQuery()
                .eq(UmsMenu::getStatus, 0)
                .in(UmsMenu::getMenuType, 0, 1)
                .orderByAsc(UmsMenu::getParentId, UmsMenu::getOrderNo));
    }

    @Override
    public List<UmsMenu> selectMenuByIds(List<Long> permIds) {
        return list(
                Wrappers.<UmsMenu>lambdaQuery()
                        .in(UmsMenu::getId, permIds)
                        .in(UmsMenu::getMenuType, 0L, 1L)
                        .eq(UmsMenu::getStatus, 0L));
    }


    @Override
    public Set<Long> selectRolePermsId(Long userId) {
        return baseMapper.selectRolePerms(userId);
    }

    @Override
    public Set<Long> selectDeptPermsId(Long userId) {
        return baseMapper.selectDeptPermsId(userId);
    }

    @Override
    public Boolean addByAddBo(MenuAddBo menuAddBo) {
        UmsMenu menu = BeanUtil.toBean(menuAddBo, UmsMenu.class);
        validEntityBeforeSave(menu);
        return save(menu);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<UmsMenu> list = getMenuChildren(id);
        if (CollUtil.isNotEmpty(list)) {
            for (UmsMenu menu : list) {
                deleteById(menu.getId());
            }
        }
        return removeById(id);
    }

    @Override
    public Boolean checkMenuUnique(UmsMenu bo) {
        return !this.list(
                        Wrappers.<UmsMenu>lambdaQuery()
                                .ne(UmsMenu::getId, bo.getId())
                                .eq(UmsMenu::getParentId, bo.getParentId())
                                .and(q -> q.eq(UmsMenu::getPath, bo.getPath()).or().eq(UmsMenu::getTitle, bo.getTitle())))
                .isEmpty();
    }

    @Override
    public List<UmsMenu> getMenuChildren(Long id) {
        return this.list(Wrappers.<UmsMenu>lambdaQuery().eq(UmsMenu::getParentId, id));
    }

    @Override
    public List<UmsMenu> getDeptPerm(Long id) {
        return baseMapper.getDeptPerm(id);
    }

    @Override
    public Boolean addDeptPerm(Long deptId, Set<Long> permIds) {
        if(permIds.size()==0){
            return true;
        }
        return baseMapper.addDeptPerm(deptId, permIds)==permIds.size();
    }

    @Override
    public Boolean removeDeptPerm(Long deptId, Set<Long> permIds) {
        if(permIds.size()==0){
            return true;
        }
        return baseMapper.removeDeptPerm(deptId, permIds)==permIds.size();
    }

    @SuppressWarnings("unused")
    private void validEntityBeforeSave(UmsMenu menu) {

    }
}
