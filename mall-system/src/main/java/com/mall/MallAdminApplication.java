package com.mall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类.
 *
 * @author 钟舒艺
 * @date 2021-06-20-15:17
 **/
@SpringBootApplication
@Slf4j
public class MallAdminApplication {
    /**
     * 启动主方法.
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
