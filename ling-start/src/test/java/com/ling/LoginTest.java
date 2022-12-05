package com.ling;

import com.ling.system.service.ISysAdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * new .
 *
 * @author 钟舒艺
 * @date 2022-10-21 16:31
 **/
@Slf4j
@SpringBootTest
public class LoginTest {

    @Autowired
    ISysAdminService sysAdminService;


    /**
     * 测试查询用户.
     */
    @Test
    void listAdmin() {


    }
}
