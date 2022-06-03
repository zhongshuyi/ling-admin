package com.ling.system.mapper;

import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.system.entity.SysPermissionUrl;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 权限跟url关联表.
 *
 * @author zsy
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface SysPermissionUrlMapper extends BaseMapperPlus<SysPermissionUrl> {


}




