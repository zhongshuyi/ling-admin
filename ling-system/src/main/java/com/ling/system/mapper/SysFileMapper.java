package com.ling.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.system.entity.SysFile;

/**
 * 文件mapper.
 *
 * @author 钟舒艺
 **/
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 根据用户id获取头像文件信息.
     *
     * @param userId 用户id
     * @return 头像文件信息
     */
    SysFile getUserAvatar(Long userId);
}
