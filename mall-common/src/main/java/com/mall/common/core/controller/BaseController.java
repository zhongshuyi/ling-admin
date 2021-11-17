package com.mall.common.core.controller;

import cn.hutool.core.util.StrUtil;
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
    protected CommonResult toAjax(int rows) {
        return rows > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 响应返回结果.
     *
     * @param result 结果
     * @return 操作结果
     */
    protected CommonResult toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 返回成功.
     *
     * @return 信息
     */
    public CommonResult success() {
        return CommonResult.success();
    }

    /**
     * 返回成功消息.
     *
     * @param message 信息
     * @return 通用返回类
     */
    public CommonResult<Void> success(String message) {
        return CommonResult.success(message);
    }

    /**
     * 返回失败消息.
     *
     * @return 信息
     */
    public CommonResult error() {
        return CommonResult.failed();
    }


    /**
     * 返回失败消息.
     *
     * @param message 信息
     * @return 信息
     */
    public CommonResult error(String message) {
        return CommonResult.failed(message);
    }

    /**
     * 页面跳转.
     *
     * @param url 充定向的url
     * @return 重定向信息
     */
    public String redirect(String url) {
        return StrUtil.format("redirect:{}", url);
    }
}
