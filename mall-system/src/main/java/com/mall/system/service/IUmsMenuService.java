package com.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.bo.MenuBo;
import com.mall.system.entity.UmsMenu;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 菜单表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
@SuppressWarnings("unused")
public interface IUmsMenuService extends IService<UmsMenu>, Serializable {

    /**
     * 获取所有菜单列表.
     *
     * @return 菜单列表
     */
    List<UmsMenu> selectMenuListAll();

    /**
     * 获取有效路由.
     *
     * @return 菜单列表.
     */
    List<UmsMenu> selectRouterListAll();


    /**
     * 根据权限id列表获取权限.
     *
     * @param permIds 权限id列表
     * @return 菜单
     */
    List<UmsMenu> selectMenuByIds(List<Long> permIds);

    /**
     * 根据用户ID查询角色权限.
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    Set<Long> selectRolePermsId(Long userId);


    /**
     * 根据用户id获取部门权限.
     *
     * @param userId 用户id
     * @return 权限的id
     */
    Set<Long> selectDeptPermsId(Long userId);


    /**
     * 增加菜单并验证.
     *
     * @param menuBo 菜单增加对象
     * @return 是否成功
     */
    Boolean addByAddBo(MenuBo menuBo);

    /**
     * 删除菜单.
     *
     * @param id 菜单id
     * @return 是否成功
     */
    Boolean deleteById(Long id);

    /**
     * 检查菜单是否唯一.
     *
     * @param menu 菜单对象
     * @return 返回是否唯一
     */
    Boolean checkMenuUnique(UmsMenu menu);

    /**
     * 获取菜单的子级菜单.
     *
     * @param id 菜单id
     * @return 子级菜单
     */
    List<UmsMenu> getMenuChildren(Long id);

    /**
     * 获取部门权限.
     *
     * @param id 部门id
     * @return 权限
     */
    Set<Long> getDeptPerm(Long id);

    /**
     * 修改部门权限.
     *
     * @param deptId 部门id
     * @param newIds 新的权限id列表
     * @return 是否修改成功
     */
    Boolean setDeptPerm(Long deptId, Set<Long> newIds);

    /**
     * 获取角色权限.
     *
     * @param id 角色id
     * @return 权限
     */
    Set<Long> getRolePerm(Long id);


    /**
     * 设置角色权限.
     *
     * @param roleId 角色id
     * @param newIds 新的权限id集合
     * @return 是否成功
     */
    Boolean setRolePerm(Long roleId, Set<Long> newIds);
}
