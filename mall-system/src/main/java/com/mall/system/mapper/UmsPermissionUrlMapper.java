package com.mall.system.mapper;

import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.system.entity.UmsPermissionUrl;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 权限跟url关联表.
 *
 * @author zsy
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsPermissionUrlMapper extends BaseMapperPlus<UmsPermissionUrl> {


}




