package com.mall;

import com.mall.mapper.UmsAdminMapper;
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
    private UmsAdminMapper umsAdminMapper;

    @Test
    public void test(){
        System.out.println(umsAdminMapper.selectList(null));
    }
}
