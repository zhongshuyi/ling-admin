package com.mall;

import com.mall.common.core.util.PageUtils;
import com.mall.system.service.IUmsRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 钟舒艺
 * @date 2021-06-24-14:48
 **/
@SpringBootTest
public class MyTest {


    @Autowired
    private IUmsRoleService iUmsRoleService;

    @Test
    public void test() {
        System.out.println(PageUtils.buildPageInfo(iUmsRoleService.pageVo(PageUtils.buildPagePlus())));
    }
}
