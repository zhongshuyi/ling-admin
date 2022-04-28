package com.mall;


import com.mall.system.mapper.UmsDeptRoleMapper;
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
class MyTest {

    @Autowired
    UmsDeptRoleMapper umsDeptRoleMapper;

    @Test
    public void test() {

    }
}
