package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.bo.query.MenuQueryBo;
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
                .eq(UmsMenu::getStatus, 0)
                .in(UmsMenu::getMenuType, 0, 1)
                .orderByDesc(UmsMenu::getParentId, UmsMenu::getOrderNo));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UmsMenu> selectMenuListAll(MenuQueryBo menuQueryBo) {
        return list(Wrappers
                .lambdaQuery(BeanUtil.toBean(menuQueryBo, UmsMenu.class))
                .orderByDesc(UmsMenu::getParentId, UmsMenu::getOrderNo));
    }


    @Override
    public List<UmsMenu> selectMenuListByUserId(MenuQueryBo menuQueryBo, Long userId) {
        return baseMapper.selectMenuListByUserId(menuQueryBo, userId);
    }

    @Override
    public Boolean addByAddBo(MenuAddBo menuAddBo) {
        UmsMenu menu = BeanUtil.toBean(menuAddBo, UmsMenu.class);
        validEntityBeforeSave(menu);
        return save(menu);
    }

    @Override
    public Boolean deleteWithValidByIds(List<Long> ids, Boolean isValid) {
        return null;
    }

    @Override
    public Boolean checkMenuUnique(UmsMenu addBo) {
        List<UmsMenu> list = this.list(
                Wrappers.<UmsMenu>lambdaQuery()
                        .eq(UmsMenu::getParentId, addBo.getParentId())
                        .and(q -> q.eq(UmsMenu::getPath, addBo.getPath()).or().eq(UmsMenu::getTitle, addBo.getTitle())));
        return !list.isEmpty();
    }

    @SuppressWarnings("unused")
    private void validEntityBeforeSave(UmsMenu menu) {

    }
}
