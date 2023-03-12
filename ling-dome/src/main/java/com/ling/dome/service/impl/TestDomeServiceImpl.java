package com.ling.dome.service.impl;

import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.dome.dto.TestDomeDTO;
import com.ling.dome.entity.TestDome;
import com.ling.dome.mapper.TestDomeMapper;
import com.ling.dome.service.ITestDomeService;
import com.ling.dome.vo.TestDomeVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类.
 * </p>
 *
 * @author 钟舒艺
 * @since 2023-03-07
 */
@Service
public class TestDomeServiceImpl
        extends ServicePlusImpl<TestDomeMapper, TestDome, TestDomeVO, TestDomeDTO>
        implements ITestDomeService {
}
