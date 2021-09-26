package com.mall;

import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
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
    private IUmsMenuService umsMenuService;

    @Test
    public void test(){

        System.out.println(umsMenuService.selectMenuListAll(null));
    }
}
