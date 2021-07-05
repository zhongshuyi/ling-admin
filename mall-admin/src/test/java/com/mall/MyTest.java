package com.mall;

import com.mall.framework.util.JwtTokenUtil;
import com.mall.mapper.UmsAdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 钟舒艺
 * @date 2021-06-24-14:48
 **/
@SpringBootTest
public class MyTest {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    public void test(){
        System.out.println(jwtTokenUtil.getSecret());
    }
}
