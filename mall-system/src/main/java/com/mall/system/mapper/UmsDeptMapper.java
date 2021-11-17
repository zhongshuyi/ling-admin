package com.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.common.core.domain.entity.UmsDept;
import java.util.List;

/**
 * 部门表 Mapper 接口
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
public interface UmsDeptMapper extends BaseMapper<UmsDept> {

    /**
     * 根据用户id获取部门列表
     *
     * @param userId 用户id
     * @return 部门列表
     */
    List<UmsDept> getDeptListByUserId(Long userId);
}
