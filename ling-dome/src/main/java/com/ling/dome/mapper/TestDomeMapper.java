package com.ling.dome.mapper;

import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.dome.entity.TestDome;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 测试表 Mapper 接口.
 * </p>
 *
 * @author 钟舒艺
 * @since 2023-03-07
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface TestDomeMapper extends BaseMapperPlus<TestDome> {

}
