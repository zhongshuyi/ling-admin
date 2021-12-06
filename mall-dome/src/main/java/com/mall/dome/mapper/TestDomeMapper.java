package com.mall.dome.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mall.common.annotation.DataScope;
import com.mall.common.core.mybatisplus.core.BaseMapperPlus;
import com.mall.dome.entity.TestDome;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 测试mapper层.
 *
 * @author zsy
 */
public interface TestDomeMapper extends BaseMapperPlus<TestDome> {

    /**
     * 重写单表selectList方法,加上数据权限注解.
     *
     * @param queryWrapper 查询条件
     * @return 查询结果.
     */
    @Override
    @DataScope
    List<TestDome> selectList(@Param(Constants.WRAPPER) Wrapper<TestDome> queryWrapper);
}
