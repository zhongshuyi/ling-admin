package com.mall.service.impl;

import com.mall.mapper.UmsAdminMapper;
import com.mall.model.UmsAdmin;
import com.mall.service.UsmAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-28-17:28
 **/
@Service
public class UsmAdminServiceImpl implements UsmAdminService {

    @Autowired
    UmsAdminMapper umsAdminMapper;

    @Override

    public List<UmsAdmin> selectAll() {
        return umsAdminMapper.selectList(null);
    }

    @Override
    public int insert(UmsAdmin umsAdmin) {
        return umsAdminMapper.insert(umsAdmin);
    }
}
