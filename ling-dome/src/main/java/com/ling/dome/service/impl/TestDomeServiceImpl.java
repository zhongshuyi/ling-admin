package com.ling.dome.service.impl;

import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.dome.entity.TestDome;
import com.ling.dome.mapper.TestDomeMapper;
import com.ling.dome.service.TestDomeService;
import com.ling.dome.vo.TestDomeVo;
import org.springframework.stereotype.Service;

/**
 * 服务层实现.
 *
 * @author zsy
 */
@Service
public class TestDomeServiceImpl extends ServicePlusImpl<TestDomeMapper, TestDome, TestDomeVo>
        implements TestDomeService {

    private static final long serialVersionUID = 1069024247199834280L;
}
