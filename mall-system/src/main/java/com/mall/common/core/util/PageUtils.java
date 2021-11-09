package com.mall.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.mybatisplus.core.PagePlus;

import java.util.regex.Pattern;

/**
 * 分页工具类
 *
 * @author 钟舒艺
 * @date 2021-10-29-23:54
 **/
public class PageUtils {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 前端传递的排序方向字符串 desc
     */
    public static final String DESC = "desc";

    /**
     * 前端传递的排序方向字符串 asc
     */
    public static final String ASC = "asc";


    /**
     * 当前记录起始索引 默认值
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 每页显示记录数 默认值 默认查全部
     */
    public static final int DEFAULT_PAGE_SIZE = Integer.MAX_VALUE;

    /**
     * 列名校验正则 首位可以是字母以及下划线.首位之后可以是字母,数字以及下划线.下划线后不能接下划线
     */
    public static final String COLUMN_NAME_REG = "/(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/";


    /**
     * 构建 plus 分页对象
     *
     * @param <T> domain 实体
     * @param <K> vo 实体
     * @return 分页对象
     */
    public static <T, K> PagePlus<T, K> buildPagePlus() {
        Integer pageNum = ServletUtils.getParameterToInt(PAGE_NUM, DEFAULT_PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(PAGE_SIZE, DEFAULT_PAGE_SIZE);
        String orderByColumn = ServletUtils.getParameter(ORDER_BY_COLUMN);
        String isAsc = ServletUtils.getParameter(IS_ASC);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        PagePlus<T, K> page = new PagePlus<>(pageNum, pageSize);
        OrderItem orderItem = buildOrderItem(orderByColumn, isAsc);
        page.addOrder(orderItem);
        return page;
    }

    /**
     * 构建 MP 普通分页对象
     *
     * @param <T> domain 实体
     * @return 分页对象
     */
    public static <T> Page<T> buildPage() {
        return buildPage(null, null);
    }


    /**
     * 构建 MP 普通分页对象
     *
     * @param defaultOrderByColumn 默认排序列
     * @param defaultIsAsc         默认排序方向
     * @param <T>                  实体
     * @return 分页对象
     */
    public static <T> Page<T> buildPage(String defaultOrderByColumn, String defaultIsAsc) {
        Integer pageNum = ServletUtils.getParameterToInt(PAGE_NUM, DEFAULT_PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(PAGE_SIZE, DEFAULT_PAGE_SIZE);
        String orderByColumn = ServletUtils.getParameter(ORDER_BY_COLUMN, defaultOrderByColumn);
        String isAsc = ServletUtils.getParameter(IS_ASC, defaultIsAsc);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        Page<T> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = buildOrderItem(orderByColumn, isAsc);
        page.addOrder(orderItem);
        return page;
    }

    /**
     * 获取排序对象,判断列名是否合法
     *
     * @param orderByColumn 排序列名
     * @param isAsc         排序方向
     * @return 排序对象
     */
    private static OrderItem buildOrderItem(String orderByColumn, String isAsc) {
        if (StrUtil.isNotBlank(orderByColumn)) {
            orderByColumn = StrUtil.toUnderlineCase(orderByColumn);
            orderByColumn = Pattern.matches(COLUMN_NAME_REG, orderByColumn) ? orderByColumn : "";
            if (ASC.equals(isAsc)) {
                return OrderItem.asc(orderByColumn);
            } else if (DESC.equals(isAsc)) {
                return OrderItem.desc(orderByColumn);
            }
        }
        return null;
    }

    /**
     * 构建plus分页结果
     *
     * @param page 分页信息
     * @param <T>  实体
     * @param <K>  Vo
     * @return 分页结果对象
     */
    public static <T, K> PageInfo<K> buildPageInfo(PagePlus<T, K> page) {
        return new PageInfo<>(page.getTotal(), page.getRecordsVo());
    }

    /**
     * 构建mp普通分页结果
     *
     * @param page 分页信息
     * @param <T>  实体
     * @return 分页结果
     */
    public static <T> PageInfo<T> buildPageInfo(Page<T> page) {
        return new PageInfo<>(page.getTotal(), page.getRecords());
    }


}
