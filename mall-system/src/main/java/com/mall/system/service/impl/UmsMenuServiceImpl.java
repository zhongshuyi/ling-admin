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
                .orderByDesc(UmsMenu::getParentId, UmsMenu::getOrderNo));
    }


    @Override
    public List<UmsMenu> selectMenuListByUserId(Long userId) {
        return baseMapper.selectMenuListByUserId(userId);
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

    @SuppressWarnings("unused")
    private void validEntityBeforeSave(UmsMenu menu) {

    }
}
