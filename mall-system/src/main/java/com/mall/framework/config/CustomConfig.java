package com.mall.framework.config;

import com.mall.framework.config.custom.AppConfig;
import com.mall.framework.config.custom.IgnoreConfig;
import com.mall.framework.config.custom.MinioInfo;
import com.mall.framework.config.custom.TokenConfig;
import com.mall.system.entity.UmsPermissionUrl;
import java.io.Serializable;
import java.util.List;
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
    private AppConfig app;

    /**
     * token配置.
     */
    private TokenConfig token;

    /**
     * 不需要拦截的地址.
     */
    private IgnoreConfig ignores;

    /**
     * 只需要登陆就能访问的url.
     * 初始化:{@link AppInit}
     */
    private List<UmsPermissionUrl> loginHasAuth;

    /**
     * minio 信息.
     */
    private MinioInfo minio;

    /**
     * 系统所有的接口,初始化在AppInit.
     * 初始化:{@link AppInit}
     */
    private List<UmsPermissionUrl> allUrl;


}
