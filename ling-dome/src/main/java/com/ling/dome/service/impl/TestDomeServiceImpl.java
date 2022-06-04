package com.ling.dome.service.impl;

import com.ling.common.core.mybatisplus.core.ServicePlusImpl;
import com.ling.dome.entity.TestDome;
import com.ling.dome.mapper.TestDomeMapper;
import com.ling.dome.service.TestDomeService;
import com.ling.dome.vo.TestDomeVo;
import com.ling.framework.util.SecurityUtils;
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

    /**
     * 保存前的数据校验.
     *
     * @param testDome 实体类
     */
    @Override
    public void validEntityBeforeSave(final TestDome testDome) {
        testDome.setUserId(SecurityUtils.getLoginUser().getSysAdmin().getId());
        super.validEntityBeforeSave(testDome);
    }
}
