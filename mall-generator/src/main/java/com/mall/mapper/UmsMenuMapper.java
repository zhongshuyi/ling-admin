package com.mall.mapper;

import com.mall.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 钟舒艺
 * @since 2021-09-14
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 查询权限列表
     * @param userId 用户id
     * @return 权限列表set
     */
    Set<String> selectPermsByUserId(Long userId);
}