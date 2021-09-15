package com.mall;

import com.mall.common.core.util.ip.IpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 钟舒艺
 * @date 2021-06-24-14:48
 **/
@SpringBootTest
public class MyTest {


    @Test
    public void test(){
        System.out.println(IpUtils.textToLong("255.255.255.255"));
    }
}
