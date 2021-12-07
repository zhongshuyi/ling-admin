package com.mall.system.mapper;


import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.system.entity.UmsRole;
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
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsRoleMapper extends BaseMapperPlus<UmsRole> {
    /**
     * 根据用户id查询角色列表.
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<UmsRole> selectRoleListByUserId(Long userId);


    /**
     * 获取角色的自定义数据范围.
     *
     * @param roleId 角色id
     * @return 部门id集合
     */
    Set<Long> getDataScope(Long roleId);

    /**
     * 增加角色自定义数据范围.
     *
     * @param roleId  角色id
     * @param deptIds 部门id
     * @return 受影响行数
     */
    Integer addDataScope(@Param("roleId") Long roleId, @Param("deptIds") Set<Long> deptIds);

    /**
     * 删除角色自定义数据范围.
     *
     * @param roleId  角色id
     * @param deptIds 部门id
     * @return 受影响行数
     */
    Integer delDataScope(@Param("roleId") Long roleId, @Param("deptIds") Set<Long> deptIds);
}
