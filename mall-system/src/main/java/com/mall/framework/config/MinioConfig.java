package com.mall.framework.config;

import io.minio.MinioClient;
import java.io.Serializable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio初始化.
 *
 * @author 钟舒艺
 **/
@Data
@Configuration
@RequiredArgsConstructor
public class MinioConfig implements Serializable {

    private static final long serialVersionUID = -9145772595340064359L;

    /**
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * 注入minio 客户端.
     *
     * @return minio 客户端
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(config.getMinio().getUrl())
                .credentials(config.getMinio().getAccessKey(), config.getMinio().getSecretKey())
                .build();
    }
}
