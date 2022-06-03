package com.ling.system.mapper;

import com.ling.common.core.mybatisplus.cache.MybatisPlusRedisCache;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import com.ling.system.dto.UserDTO;
import com.ling.system.entity.SysAdmin;
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
public interface SysAdminMapper extends BaseMapperPlus<SysAdmin> {


    /**
     * 根据角色id获取用户列表.
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<SysAdmin> getUserListByRoleId(Long roleId);


    /**
     * 自定义分页查询.
     *
     * @param bo 查询条件
     * @return 分页后结果及总行数
     */
    List<SysAdmin> queryUserList(UserDTO bo);

    /**
     * 根据用户名获取用户信息.
     *
     * @param userName 用户名
     * @return 用户信息
     */
    SysAdmin getSysAdminByUserName(String userName);

    /**
     * 根据id查询用户.
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysAdmin getSysAdminById(Long userId);

    /**
     * 获取用户头像地址.
     *
     * @param userId 用户id
     * @return 原始头像地址
     */
    String getUserAvatar(Long userId);
}
