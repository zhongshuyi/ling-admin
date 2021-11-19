package com.mall;


import com.mall.system.service.IUmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类.
 *
 * @author 钟舒艺
 * @date 2021-11-11-14:49
 **/
@SpringBootTest
@Slf4j
public class MyTest {

    @Autowired
    IUmsAdminService umsAdminService;

    @Test
    void test() {
        log.info("");
    }
}
