package com.mall.service;

import com.mall.model.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
public interface IUmsMenuService extends IService<UmsMenu> {

    /**
     * 获取所有菜单列表
     *
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListByUserId(Long userId);
}
