package com.ling.system.mapper;

import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.system.entity.SysDeptRole;
import java.util.Set;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * 部门角色mapper.
 *
 * @author 钟舒艺
 **/
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface SysDeptRoleMapper extends BaseMapperPlus<SysDeptRole> {

    /**
     * 根据用户id获取该用户拥有的部门角色id.
     *
     * @param deptId 部门id
     * @param userId 用户id
     * @return 部门角色id
     */
    Set<Long> getDeptRoleIdsByUserId(
            @Param("deptId") Long deptId,
            @Param("userId") Long userId);

    /**
     * 增加用户的部门角色.
     *
     * @param deptId      部门id
     * @param userId      用户id
     * @param deptRoleIds 部门角色id集合
     * @return 受影响行数
     */
    Integer addDeptRoleToUser(
            @Param("deptId") Long deptId,
            @Param("userId") Long userId,
            @Param("deptRoleIds") Set<Long> deptRoleIds);

    /**
     * 增加用户的部门角色.
     *
     * @param deptId      部门id
     * @param userId      用户id
     * @param deptRoleIds 部门角色id集合
     * @return 受影响行数
     */
    Integer delDeptRoleToUser(
            @Param("deptId") Long deptId,
            @Param("userId") Long userId,
            @Param("deptRoleIds") Set<Long> deptRoleIds);
}
