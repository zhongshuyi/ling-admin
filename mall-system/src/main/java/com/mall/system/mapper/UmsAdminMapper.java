package com.mall.system.mapper;

import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import java.util.List;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 用户信息表 Mapper 接口
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsAdminMapper extends BaseMapperPlus<UmsAdmin> {
    /**
     * 根据角色id获取用户列表
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);
}
