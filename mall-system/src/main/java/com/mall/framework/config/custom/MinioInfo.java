package com.mall.framework.config.custom;

import java.io.Serializable;
import lombok.Data;

/**
 * minio信息.
 *
 * @author 钟舒艺
 **/
@Data
public class MinioInfo implements Serializable {

    private static final long serialVersionUID = -6540227793861847139L;

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
}
