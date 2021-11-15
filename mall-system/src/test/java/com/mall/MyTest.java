package com.mall;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.system.service.IUmsAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 钟舒艺
 * @date 2021-11-11-14:49
 **/
@SpringBootTest
public class MyTest {

    @Autowired
    IUmsAdminService umsAdminService;

    @Test
    void test() {
        System.out.println(umsAdminService.getOne(Wrappers.<UmsAdmin>lambdaQuery().eq(UmsAdmin::getUsername,"zhong").last("limit 1")));
    }
}
