package com.ling;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.Unsafe;

/**
 * 项目启动类.
 *
 * @author 钟舒艺
 * @since 2022-10-20 19:15
 **/
@Slf4j
@SpringBootApplication
public class LingAdminApplication {
    /**
     * 启动主方法.
     *
     * @param args 参数
     */
    public static void main(final String[] args) {
        disableWarning(); //禁用警告
        SpringApplication.run(LingAdminApplication.class, args);
        LingAdminApplication.log.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
    }


    /**
     * 消除反射的警告.
     */
    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }
}
