package com.ling.framework.config.properties;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 访问权限需要忽略的路径.
 *
 * @author 钟舒艺
 **/
@Data
public class IgnoreProperties implements Serializable {

    private static final long serialVersionUID = 2041198721049541252L;

    /**
     * 需要忽略的 URL 格式，不考虑请求方法.
     */
    private List<String> pattern = Lists.newArrayList();

    /**
     * 需要忽略的 GET 请求.
     */
    private List<String> get = Lists.newArrayList();

    /**
     * 需要忽略的 POST 请求.
     */
    private List<String> post = Lists.newArrayList();

    /**
     * 需要忽略的 DELETE 请求.
     */
    private List<String> delete = Lists.newArrayList();

    /**
     * 需要忽略的 PUT 请求.
     */
    private List<String> put = Lists.newArrayList();

    /**
     * 需要忽略的 HEAD 请求.
     */
    private List<String> head = Lists.newArrayList();

    /**
     * 需要忽略的 PATCH 请求.
     */
    private List<String> patch = Lists.newArrayList();

    /**
     * 需要忽略的 OPTIONS 请求.
     */
    private List<String> options = Lists.newArrayList();

    /**
     * 需要忽略的 TRACE 请求.
     */
    private List<String> trace = Lists.newArrayList();

    /**
     * 需要忽略的 CONNECT 请求.
     */
    private List<String> connect = Lists.newArrayList();

}
