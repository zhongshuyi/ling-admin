package com.mall;


import com.mall.system.service.IUmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类.
 *
 * @author 钟舒艺
 **/
@SpringBootTest
@Slf4j
public class MyTest {

    @Autowired(required = false)
    IUmsRoleService umsRoleService;

    @Test
    void test() {
        log.info(umsRoleService.selectRoleListByUserId(1L).toString());
    }
}
