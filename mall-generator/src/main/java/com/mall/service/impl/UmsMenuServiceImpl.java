package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.model.UmsMenu;
import com.mall.mapper.UmsMenuMapper;
import com.mall.service.IUmsMenuService;
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
    public List<UmsMenu> selectMenuListByUserId(Long userId) {
        return baseMapper.selectMenuListByUserId(userId);
    }
}
