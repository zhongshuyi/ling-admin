package com.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.system.entity.UmsMenu;
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
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 查询权限列表.
     *
     * @param userId 用户id
     * @return 权限列表set
     */
    Set<Long> selectUserPermsIdsById(Long userId);

    /**
     * 查询部门的权限列表.
     *
     * @param userId 用户id
     * @return 权限列表
     */
    Set<Long> selectDeptPermsId(Long userId);


    /**
     * 获取部门权限.
     *
     * @param id 部门id
     * @return 权限
     */
    Set<Long> selectDeptPerm(Long id);

    /**
     * 增加部门权限.
     *
     * @param deptId  部门id
     * @param permIds 权限id
     * @return 改变行数
     */
    Integer addDeptPerm(@Param("deptId") Long deptId, @Param("permIds") Set<Long> permIds);

    /**
     * 删除部门权限.
     *
     * @param deptId  部门id
     * @param permIds 权限id
     * @return 是否成功
     */
    Integer delDeptPerm(@Param("deptId") Long deptId, @Param("permIds") Set<Long> permIds);


    /**
     * 增加部门权限.
     *
     * @param roleId  部门id
     * @param permIds 权限id
     * @return 改变行数
     */
    Integer addRolePerm(@Param("roleId") Long roleId, @Param("permIds") Set<Long> permIds);

    /**
     * 删除部门权限.
     *
     * @param roleId  部门id
     * @param permIds 权限id
     * @return 是否成功
     */
    Integer delRolePerm(@Param("roleId") Long roleId, @Param("permIds") Set<Long> permIds);

    /**
     * 获取角色权限.
     *
     * @param id 角色id
     * @return 权限
     */
    Set<Long> selectRolePerm(Long id);
}
