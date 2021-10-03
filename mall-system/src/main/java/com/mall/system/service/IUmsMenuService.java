package com.mall.system.service;

import com.mall.common.core.domain.entity.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.bo.add.MenuAddBo;

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
     * 获取有效路由
     * @return 菜单列表
     */
    List<UmsMenu> selectRouterListAll();


    /**
     * 根据用户ID查询菜单
     *
     * @param userId      用户ID
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListByUserId( Long userId);

    /**
     * 增加菜单并验证
     * @param menuAddBo 菜单增加对象
     * @return 是否成功
     */
    Boolean addByAddBo(MenuAddBo menuAddBo);

    /**
     * 删除菜单
     * @param id 菜单id
     * @return  是否成功
     */
    Boolean deleteById(Long id );

    /**
     * 检查菜单是否唯一
     * @param menu 菜单对象
     * @return 返回是否唯一
     */
    Boolean checkMenuUnique(UmsMenu menu);

    /**
     * 获取菜单的子级菜单
     * @param id 菜单id
     * @return 子级菜单
     */
    List<UmsMenu> getMenuChildren(Long id);

}
