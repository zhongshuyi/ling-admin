package com.mall.system.mapper;

import com.mall.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import java.util.List;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 用户信息表 Mapper 接口.
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@CacheNamespace(
        implementation = MybatisPlusRedisCache.class,
        eviction = MybatisPlusRedisCache.class)
public interface UmsAdminMapper extends BaseMapperPlus<UmsAdmin> {
    /**
     * 根据角色id获取用户列表.
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UmsAdmin> getUserListByRoleId(Long roleId);


    /**
     * 自定义分页查询.
     *
     * @param bo 查询条件
     * @return 分页后结果及总行数
     */
    List<UmsAdmin> queryUserList(UserBo bo);


    /**
     * 根据用户名获取用户信息.
     *
     * @param userName 用户名
     * @return 用户信息
     */
    UmsAdmin getUmsAdminByUserName(String userName);
}
