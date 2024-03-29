package com.ling.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.common.core.domain.model.SysMenu;
import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单表 Mapper 接口.
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id查询权限列表.
     *
     * @param userId 用户id
     * @return 权限列表set
     */
    Set<Long> listUserPermsIdsById(Long userId);


    /**
     * 根据用户id查询权限列表.
     *
     * @param userId 用户id.
     * @return 权限列表set
     */
    Set<SysMenu> listUserPermsById(Long userId);


    /**
     * 查询部门的权限列表.
     *
     * @param userId 用户id
     * @return 权限列表
     */
    Set<Long> listDeptRolePermsIdsByUserId(Long userId);


    /**
     * 通过用户id查询部门的权限列表.
     *
     * @param userId 用户id
     * @return 权限列表
     */
    Set<SysMenu> listDeptRolePermsByUserId(Long userId);


    /**
     * 获取部门权限.
     *
     * @param id 部门id
     * @return 权限
     */
    Set<Long> listDeptPerm(Long id);

    /**
     * 增加部门权限.
     *
     * @param deptId  部门id
     * @param permIds 权限id
     * @return 改变行数
     */
    Integer insertDeptPermByDeptId(
            @Param("deptId") Long deptId,
            @Param("permIds") Set<Long> permIds
    );

    /**
     * 删除部门权限.
     *
     * @param deptId  部门id
     * @param permIds 权限id
     * @return 是否成功
     */
    Integer deleteDeptPermByDeptId(
            @Param("deptId") Long deptId,
            @Param("permIds") Set<Long> permIds
    );

    /**
     * 根据菜单权限id删除部门权限.
     *
     * @param permId 菜单权限id
     * @return 成功条数
     */
    Integer deleteDeptPermByPermId(Long permId);


    /**
     * 增加部门权限.
     *
     * @param roleId  部门id
     * @param permIds 权限id
     * @return 改变行数
     */
    Integer insertRolePermByRoleId(
            @Param("roleId") Long roleId,
            @Param("permIds") Set<Long> permIds
    );

    /**
     * 删除部门权限.
     *
     * @param roleId  角色id
     * @param permIds 权限id
     * @return 是否成功
     */
    Integer deleteRolePermByRoleId(
            @Param("roleId") Long roleId,
            @Param("permIds") Set<Long> permIds
    );


    /**
     * 根据权限id删除角色菜单或权限.
     *
     * @param permId 菜单权限id
     * @return 删除条数
     */
    Integer deleteRolePermByPermId(Long permId);

    /**
     * 根据权限角色id删除部门角色的权限或菜单.
     *
     * @param permId 菜单权限id
     * @return 删除条数
     */
    Integer deleteDeptRolePermByPermId(Long permId);

    /**
     * 获取角色权限.
     *
     * @param id 角色id
     * @return 权限
     */
    Set<Long> listRolePermIds(Long id);


    /**
     * 获取部门角色权限.
     *
     * @param deptRoleId 部门角色id
     * @return 权限id
     */
    Set<Long> listDeptRolePermIds(Long deptRoleId);


    /**
     * 添加部门角色权限.
     *
     * @param deptRoleId 部门角色id.
     * @param permIds    权限id
     * @return 受影响行数
     */
    Integer insertDeptRolePerm(
            @Param("deptRoleId") Long deptRoleId,
            @Param("permIds") Set<Long> permIds
    );

    /**
     * 删除部门角色权限.
     *
     * @param deptRoleId 部门id
     * @param permIds    权限id
     * @return 是否成功
     */
    Integer deleteDeptRolePerm(
            @Param("roleId") Long deptRoleId,
            @Param("permIds") Set<Long> permIds
    );

    /**
     * 查询部门所拥有权限的菜单.
     *
     * @param deptId 部门id
     * @return 该部门所拥有权限的菜单
     */
    List<SysMenu> listDeptPermMenu(Long deptId);
}
