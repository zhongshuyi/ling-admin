package com.mall;

import com.mall.common.core.util.ip.IpUtils;
import com.mall.service.IUmsMenuService;
import com.mall.util.MenuUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

/**
 * @author 钟舒艺
 * @date 2021-06-24-14:48
 **/
@SpringBootTest
public class MyTest {


    @Autowired
    private IUmsMenuService umsMenuService;

    @Test
    public void test(){
        System.out.println(MenuUtil.buildMenus(umsMenuService.selectMenuListByUserId(2L),0L));
    }
}
