package com.ling.framework.config.custom;

import java.io.Serializable;
import lombok.Data;

/**
 * 微信小程序相关配置.
 *
 * @author 钟舒艺
 **/
@Data
public class WxConfig implements Serializable {

    private static final long serialVersionUID = 8428933965465981610L;
    /**
     * 小程序的appid.
     */
    private String appId;

    /**
     * 公众平台申请的appSecret.
     */
    private String appSecret;
}
