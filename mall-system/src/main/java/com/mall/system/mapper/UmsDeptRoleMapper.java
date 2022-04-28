package com.mall.system.mapper;

import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.system.entity.UmsDeptRole;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 部门角色mapper.
 *
 * @author 钟舒艺
 **/
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsDeptRoleMapper extends BaseMapperPlus<UmsDeptRole> {
}
