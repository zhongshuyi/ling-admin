package com.mall.common.core.controller;

import cn.hutool.core.util.StrUtil;

import com.mall.common.core.domain.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
@SuppressWarnings("unused")
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected CommonResult toAjax(int rows)
    {
        return rows > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected CommonResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     * @return 信息
     */
    public CommonResult success()
    {
        return CommonResult.success();
    }

    /**
     * 返回失败消息
     * @return 信息
     */
    public CommonResult error()
    {
        return CommonResult.failed();
    }

    /**
     * 返回成功消息
     */
    public CommonResult<Void> success(String message)
    {
        return CommonResult.success(message);
    }

    /**
     * 返回失败消息
     * @return 信息
     */
    public CommonResult error(String message)
    {
        return CommonResult.failed(message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StrUtil.format("redirect:{}", url);
    }
}
