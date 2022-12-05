package com.ling.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.system.entity.SysFile;
import java.io.Serializable;

/**
 * 文件服务层.
 *
 * @author 钟舒艺
 **/
public interface ISysFileService extends IService<SysFile>, Serializable {

    /**
     * 根据用户id获取头像文件信息.
     *
     * @param userId 用户id
     * @return 头像文件信息
     */
    SysFile getUserAvatar(Long userId);
}
