package com.mall.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.bo.query.MenuQueryBo;
import com.mall.system.mapper.UmsMenuMapper;
import com.mall.system.service.IUmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
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
    public List<UmsMenu> selectMenuListAll() {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        queryWrapper.in("menu_type", 0, 1);
        queryWrapper.orderByDesc("parent_id","order_no");
        return list(queryWrapper);
    }

    @Override
    public List<UmsMenu> selectMenuListAll(MenuQueryBo menuQueryBo) {
        UmsMenu m = BeanUtil.toBean(menuQueryBo, UmsMenu.class);
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>(m);
        queryWrapper.orderByDesc("parent_id","order_no");
        return list(queryWrapper);
    }


    @Override
    public List<UmsMenu> selectMenuListByUserId( MenuQueryBo menuQueryBo ,Long userId) {
        return baseMapper.selectMenuListByUserId(menuQueryBo ,userId);
    }

    @Override
    public Boolean addByAddBo(MenuAddBo menuAddBo) {
        UmsMenu menu = BeanUtil.toBean(menuAddBo,UmsMenu.class);
        validEntityBeforeSave(menu);
        return save(menu);
    }


    private void validEntityBeforeSave(UmsMenu menu){

    }
}
