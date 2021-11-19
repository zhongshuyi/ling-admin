package com.mall.system.mapper;


import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.system.entity.UmsRole;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

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
}
