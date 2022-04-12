package com.mall.framework.config.custom;

import java.io.Serializable;
import lombok.Data;

/**
 * token配置.
 *
 * @author 钟舒艺
 **/
@Data
public class TokenConfig implements Serializable {

    private static final long serialVersionUID = -3774141801897864799L;

    /**
     * token存储的请求头.
     */
    private String tokenHeader;

    /**
     * token加解密使用的密钥.
     */
    private String secret;

    /**
     * token的超期限时间.
     */
    private Long expiration;

    /**
     * token前缀.
     */
    private String tokenPrefix;

    /**
     * token中储存的uuid键名.
     */
    private String userKey;

}
