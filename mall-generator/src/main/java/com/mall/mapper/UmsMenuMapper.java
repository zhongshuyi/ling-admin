package com.mall.mapper;

import com.mall.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-07-07
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {
    /**
     *  根据用户id查询权限
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> selectPermsByUserId(Long userId);
}
