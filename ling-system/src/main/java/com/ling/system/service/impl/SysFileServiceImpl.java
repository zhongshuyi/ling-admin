package com.ling.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.system.entity.SysFile;
import com.ling.system.mapper.SysFileMapper;
import com.ling.system.service.ISysFileService;
import org.springframework.stereotype.Service;

/**
 * 服务实现层.
 *
 * @author 钟舒艺
 **/
@Service
public class SysFileServiceImpl
        extends ServiceImpl<SysFileMapper, SysFile>
        implements ISysFileService {

    @Override
    public final SysFile selectUserAvatar(final Long userId) {
        return getBaseMapper().getUserAvatar(userId);
    }
}
