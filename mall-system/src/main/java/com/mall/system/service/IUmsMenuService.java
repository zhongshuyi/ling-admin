package com.mall.system.service;

import com.mall.common.core.domain.entity.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.bo.query.MenuQueryBo;

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
     * 获取所有菜单列表
     *
     * @param menuQueryBo 查询条件
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListAll(MenuQueryBo menuQueryBo);

    /**
     * 根据用户ID查询菜单
     *
     * @param menuQueryBo 查询条件
     * @param userId      用户ID
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListByUserId(MenuQueryBo menuQueryBo, Long userId);

    /**
     * 增加菜单并验证
     * @param menuAddBo 菜单增加对象
     * @return 是否成功
     */
    Boolean addByAddBo(MenuAddBo menuAddBo);

    /**
     * 验证后删除菜单
     * @param ids 菜单列表
     * @param isValid 菜单列表
     * @return  是否成功
     */
    Boolean deleteWithValidByIds(List<Long> ids , Boolean isValid);

    /**
     * 检查菜单是否唯一
     * @param addBo 添加菜单对象
     * @return 返回是否唯一
     */
    Boolean checkMenuUnique(UmsMenu addBo);
}
