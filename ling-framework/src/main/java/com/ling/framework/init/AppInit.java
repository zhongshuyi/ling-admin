package com.ling.framework.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目初始化时执行.
 *
 * @author 钟舒艺
 * @since 2022-10-22 20:21
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class AppInit implements CommandLineRunner {

    @Override
    public final void run(final String... args) {
        // 项目启动时执行
    }
}
