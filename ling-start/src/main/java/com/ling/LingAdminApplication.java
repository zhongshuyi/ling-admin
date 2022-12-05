package com.ling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类.
 *
 * @author 钟舒艺
 * @since 2022-10-20 19:15
 **/
@SpringBootApplication
@Slf4j
public class LingAdminApplication {
    /**
     * 启动主方法.
     *
     * @param args 参数
     */
    public static void main(final String[] args) {
        SpringApplication.run(LingAdminApplication.class, args);
        LingAdminApplication.log.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
