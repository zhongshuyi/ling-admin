package com.ling.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.system.dto.SysMenuDTO;
import com.ling.system.entity.SysMenu;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 菜单表 服务类.
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
public interface ISysMenuService extends IService<SysMenu>, Serializable {

    /**
     * 获取所有菜单列表.
     *
     * @return 菜单列表
     */
    List<SysMenu> listMenuAll();

    /**
     * 获取有效路由.
     *
     * @return 菜单列表.
     */
    List<SysMenu> listRouterAll();


    /**
     * 根据权限id列表获取权限.
     *
     * @param permIds 权限id列表
     * @return 菜单
     */
    List<SysMenu> listMenuByIds(List<Long> permIds);

    /**
     * 根据用户ID查询角色权限.
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    Set<Long> listRolePermsId(Long userId);


    /**
     * 根据用户id获取部门权限.
     *
     * @param userId 用户id
     * @return 权限的id
     */
    Set<Long> listDeptPermsId(Long userId);


    /**
     * 增加菜单并验证.
     *
     * @param menuDTO 菜单增加对象
     * @return 是否成功
     */
    Boolean saveMenuByDTO(SysMenuDTO menuDTO);

    /**
     * 删除菜单.
     *
     * @param id 菜单id
     * @return 是否成功
     */
    Boolean removeMenuById(Long id);

    /**
     * 检查菜单是否唯一.
     *
     * @param menu 菜单对象
     * @return 返回是否唯一
     */
    Boolean checkMenuUnique(SysMenu menu);

    /**
     * 获取菜单的子级菜单.
     *
     * @param id 菜单id
     * @return 子级菜单
     */
    List<SysMenu> listMenuChildren(Long id);


    /**
     * 查询部门所拥有权限的菜单.
     *
     * @param deptId 部门id
     * @return 该部门所拥有权限的菜单
     */
    List<SysMenu> listDeptPermMenu(Long deptId);

    /**
     * 获取部门权限.
     *
     * @param id 部门id
     * @return 权限
     */
    Set<Long> listDeptPerm(Long id);

    /**
     * 修改部门权限.
     *
     * @param deptId 部门id
     * @param newIds 新的权限id列表
     * @return 是否修改成功
     */
    Boolean updateDeptPerm(
            Long deptId,
            Set<Long> newIds
    );

    /**
     * 获取角色权限.
     *
     * @param id 角色id
     * @return 权限
     */
    Set<Long> listRolePerm(Long id);


    /**
     * 设置角色权限.
     *
     * @param roleId 角色id
     * @param newIds 新的权限id集合
     * @return 是否成功
     */
    Boolean updateRolePerm(
            Long roleId,
            Set<Long> newIds
    );

    /**
     * 获取部门角色的权限.
     *
     * @param deptRoleId 部门角色id
     * @return 权限id
     */
    Set<Long> listDeptRolePermIds(Long deptRoleId);


    /**
     * 设置部门角色权限.
     *
     * @param deptRoleId 部门角色权限
     * @param newIds     新的权限id
     * @return 是否成功
     */
    Boolean updateDeptRolePerm(
            Long deptRoleId,
            Set<Long> newIds
    );
}
