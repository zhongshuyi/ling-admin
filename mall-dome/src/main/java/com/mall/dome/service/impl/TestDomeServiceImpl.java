package com.mall.dome.service.impl;

import com.mall.common.core.mybatisplus.core.ServicePlusImpl;
import com.mall.dome.entity.TestDome;
import com.mall.dome.mapper.TestDomeMapper;
import com.mall.dome.service.TestDomeService;
import com.mall.dome.vo.TestDomeVo;
import org.springframework.stereotype.Service;

/**
 * 服务层实现.
 *
 * @author zsy
 */
@Service
public class TestDomeServiceImpl extends ServicePlusImpl<TestDomeMapper, TestDome, TestDomeVo>
        implements TestDomeService {

}
