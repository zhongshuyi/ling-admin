package com.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.system.entity.UmsDept;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 部门表 Mapper 接口.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UmsDeptMapper extends BaseMapper<UmsDept> {

    /**
     * 根据用户id获取部门列表.
     *
     * @param userId 用户id
     * @return 部门列表
     */
    List<UmsDept> selectDeptListByUserId(Long userId);

    /**
     * 根据用户id查询部门id.
     *
     * @param userId 用户id
     * @return 部门id
     */
    Set<Long> selectDeptIdsByUserId(Long userId);

    /**
     * 增加用户的部门.
     *
     * @param userId  用户id
     * @param deptIds 部门id集合
     * @return 受影响行数
     */
    Integer addUserDept(
            Long userId,
            Set<Long> deptIds
    );

    /**
     * 删除用户的部门.
     *
     * @param userId  用户id
     * @param deptIds 要删除的部门id
     * @return 受影响行数
     */
    Integer delUserDept(
            Long userId,
            Set<Long> deptIds
    );
}
