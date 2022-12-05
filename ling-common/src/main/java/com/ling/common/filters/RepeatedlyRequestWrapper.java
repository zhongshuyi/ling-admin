package com.ling.common.filters;

import cn.hutool.core.io.IoUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 构建可重复读取inputStream的request.
 *
 * @author ruoyi
 */
@SuppressWarnings("unused")
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 储存request信息.
     */
    private final byte[] body;

    /**
     * 获取信息并储存.
     *
     * @param request  请求信息
     * @param response 返回信息
     * @throws IOException Io异常
     */
    public RepeatedlyRequestWrapper(
            final HttpServletRequest request,
            final ServletResponse response
    ) throws IOException {
        super(request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        body = IoUtil.readUtf8(request.getInputStream()).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public final BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public final ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public int available() {
                return body.length;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(final ReadListener readListener) {
                // 不设置监听器
            }
        };
    }
}
