package com.mall.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.system.entity.UmsFile;
import com.mall.system.mapper.UmsFileMapper;
import com.mall.system.service.IUmsFileService;
import org.springframework.stereotype.Service;

/**
 * 服务实现层.
 *
 * @author 钟舒艺
 **/
@Service
public class UmsFileServiceImpl
        extends ServiceImpl<UmsFileMapper, UmsFile>
        implements IUmsFileService {
}
