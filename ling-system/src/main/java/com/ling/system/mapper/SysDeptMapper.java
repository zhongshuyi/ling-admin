package com.ling.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.system.entity.SysDept;
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
public interface SysDeptMapper extends BaseMapper<SysDept>, BaseMapperPlus<SysDept> {

    /**
     * 根据用户id获取部门列表.
     *
     * @param userId 用户id
     * @return 部门列表
     */
    List<SysDept> selectDeptListByUserId(Long userId);

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

    /**
     * 获取子部门列表.
     *
     * @param parentId 父id
     * @return 子部门列表
     */
    List<SysDept> selectDeptChildren(Long parentId);
}
