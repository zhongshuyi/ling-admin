package com.mall;


import cn.hutool.core.lang.UUID;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.File;
import java.io.FileInputStream;
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
public class MyTest {
    @Autowired
    MinioClient minioClient;

    @Test
    void test() {
        File file = new File("D:\\backups\\头像.jpg");
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object("img/"+UUID.fastUUID() + ".jpg")
                            .bucket("mall")
                            .stream(new FileInputStream(file), file.length(), -1)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
