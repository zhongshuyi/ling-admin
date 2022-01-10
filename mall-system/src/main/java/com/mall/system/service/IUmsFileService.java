package com.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.system.entity.UmsFile;
import java.io.Serializable;

/**
 * 文件服务层.
 *
 * @author 钟舒艺
 **/
public interface IUmsFileService extends IService<UmsFile>, Serializable {

    /**
     * 根据用户id获取头像文件信息.
     *
     * @param userId 用户id
     * @return 头像文件信息
     */
    UmsFile getUserAvatar(Long userId);
}
