package com.ling.framework.config;

import com.ling.framework.config.properties.AppProperties;
import com.ling.framework.config.properties.IgnoreProperties;
import com.ling.framework.config.properties.MinioInfoProperties;
import java.io.Serializable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义的配置.
 *
 * @author 钟舒艺
 **/
@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomConfig implements Serializable {


    private static final long serialVersionUID = -6318082542925658066L;
    /**
     * 系统自定义信息.
     */
    private AppProperties app;

    /**
     * 不需要拦截的地址.
     */
    private IgnoreProperties ignores;

    /**
     * minio 信息.
     */
    private MinioInfoProperties minio;


}
