package com.ling.system.mapper;


import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.system.entity.SysRole;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * 角色信息表 Mapper 接口.
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface SysRoleMapper extends BaseMapperPlus<SysRole> {
    /**
     * 根据用户id查询角色列表.
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUserId(Long userId);


    /**
     * 根据用户id查询角色.
     *
     * @param userId 用户id
     * @return 角色id
     */
    Set<Long> selectRoleIdsByUserId(Long userId);


    /**
     * 增加用户角色.
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     * @return 受影响行数
     */
    Integer addUserRole(
            @Param("userId") Long userId,
            @Param("roleIds") Set<Long> roleIds
    );


    /**
     * 获取角色的自定义数据范围.
     *
     * @param roleId 角色id
     * @return 部门id集合
     */
    Set<Long> selectDataScope(Long roleId);

    /**
     * 增加角色自定义数据范围.
     *
     * @param roleId  角色id
     * @param deptIds 部门id
     * @return 受影响行数
     */
    Integer addDataScope(
            @Param("roleId") Long roleId,
            @Param("deptIds") Set<Long> deptIds
    );

    /**
     * 删除角色自定义数据范围.
     *
     * @param roleId  角色id
     * @param deptIds 部门id
     * @return 受影响行数
     */
    Integer delDataScope(
            @Param("roleId") Long roleId,
            @Param("deptIds") Set<Long> deptIds
    );

    /**
     * 增加用户角色.
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     * @return 受影响行数
     */
    Integer delUserRole(
            @Param("userId") Long userId,
            @Param("roleIds") Set<Long> roleIds
    );
}
