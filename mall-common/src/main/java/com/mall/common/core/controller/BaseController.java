package com.mall.common.core.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.mall.common.core.domain.CommonResult;
import lombok.extern.slf4j.Slf4j;

/**
 * web层通用数据处理.
 *
 * @author ruoyi
 */
@SuppressWarnings("unused")
@Slf4j
public class BaseController {

    /**
     * 响应返回结果.
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected CommonResult<Void> toAjax(final Integer rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果.
     *
     * @param result 结果
     * @return 操作结果
     */
    protected CommonResult<Void> toAjax(final Boolean result) {
        return Boolean.TRUE.equals(result) ? success() : error();
    }

    /**
     * 返回成功.
     *
     * @return 信息
     */
    private CommonResult<Void> success() {
        return CommonResult.success();
    }

    /**
     * 返回成功消息.
     *
     * @param message 信息
     * @return 通用返回类
     */
    public CommonResult<Void> success(final String message) {
        return CommonResult.success(message);
    }

    /**
     * 返回失败消息.
     *
     * @return 信息
     */
    private CommonResult<Void> error() {
        return CommonResult.failed();
    }


    /**
     * 返回失败消息.
     *
     * @param message 信息
     * @return 信息
     */
    protected CommonResult<Void> error(final String message) {
        return CommonResult.failed(message);
    }

    /**
     * 页面跳转.
     *
     * @param url 充定向的url
     * @return 重定向信息
     */
    protected String redirect(final String url) {
        return CharSequenceUtil.format("redirect:{}", url);
    }
}
