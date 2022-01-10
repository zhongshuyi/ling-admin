package com.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.system.entity.UmsFile;

/**
 * 文件mapper.
 *
 * @author 钟舒艺
 **/
public interface UmsFileMapper extends BaseMapper<UmsFile> {

    /**
     * 根据用户id获取头像文件信息.
     *
     * @param userId 用户id
     * @return 头像文件信息
     */
    UmsFile getUserAvatar(Long userId);
}
