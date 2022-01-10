package com.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.system.entity.UmsDept;
import java.util.List;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 部门表 Mapper 接口.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsDeptMapper extends BaseMapper<UmsDept> {

    /**
     * 根据用户id获取部门列表.
     *
     * @param userId 用户id
     * @return 部门列表
     */
    List<UmsDept> getDeptListByUserId(Long userId);
}
