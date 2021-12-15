package com.mall.framework.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio初始化.
 *
 * @author 钟舒艺
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    /**
     * minio地址+端口号.
     */
    private String url;

    /**
     * minio用户名.
     */
    private String accessKey;

    /**
     * minio密码.
     */
    private String secretKey;

    /**
     * 文件桶的名称.
     */
    private String bucketName;

    /**
     * 注入minio 客户端.
     *
     * @return minio 客户端
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
