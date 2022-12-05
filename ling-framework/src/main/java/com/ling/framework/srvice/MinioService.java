package com.ling.framework.srvice;

import com.ling.framework.config.CustomConfig;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

/**
 * minio工具类.
 *
 * @author 钟舒艺
 **/

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MinioService implements Serializable {

    private static final long serialVersionUID = -6077932413515629194L;
    /**
     * minio操作对象.
     */
    private final transient MinioClient minioClient;

    /**
     * 配置信息.
     */
    private final CustomConfig config;


    /**
     * 创建桶 bucket.
     *
     * @param bucketName 桶名
     */
    @SneakyThrows(Exception.class)
    public void createBucket(final String bucketName) {
        final boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    /**
     * 文件上传.
     *
     * @param fileName    文件名
     * @param stream      文件流
     * @param contentType 请求头
     */
    @SneakyThrows(Exception.class)
    public void upload(
            final String fileName,
            final InputStream stream,
            final String contentType
    ) {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .object(fileName)
                        .bucket(config.getMinio().getBucketName())
                        .contentType(contentType)
                        .stream(stream, stream.available(), -1)
                        .build()
        );
    }


    /**
     * 删除文件.
     *
     * @param fileName 文件名
     */
    @SneakyThrows(Exception.class)
    public void deleteFile(final String fileName) {
        minioClient.removeObject(
                RemoveObjectArgs
                        .builder()
                        .bucket(config.getMinio().getBucketName())
                        .object(fileName)
                        .build()
        );
    }

    /**
     * 下载文件.
     *
     * @param fileName 文件名
     * @param response 响应对象
     */
    @SneakyThrows(Exception.class)
    public void download(
            final String fileName,
            final HttpServletResponse response
    ) {
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment;filename="
                        + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
        );
        final GetObjectResponse getOutputStream =
                minioClient.getObject(
                        GetObjectArgs
                                .builder()
                                .bucket(config.getMinio().getBucketName())
                                .object(fileName)
                                .build()
                );
        IOUtils.copy(getOutputStream, response.getOutputStream());
        getOutputStream.close();
    }
}
