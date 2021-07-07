package com.mall.mapper;

import com.mall.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {
    /**
     * 根据用户id查询角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<UmsRole> selectRoleListByUserId(Long userId);
}
