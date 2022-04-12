package com.mall.common.core.mybatisplus.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.common.util.ServletUtils;

/**
 * 分页工具类.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
public final class PageUtils {
    /**
     * 当前记录起始索引.
     */
    private static final String PAGE_NUM = "pageNum";
    /**
     * 每页显示记录数.
     */
    private static final String PAGE_SIZE = "pageSize";
    /**
     * 排序列.
     */
    private static final String ORDER_BY_COLUMN = "orderByColumn";
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private static final String IS_ASC = "isAsc";
    /**
     * 前端传递的排序方向字符串 desc.
     */
    private static final String DESC = "desc";
    /**
     * 前端传递的排序方向字符串 asc.
     */
    private static final String ASC = "asc";
    /**
     * 当前记录起始索引 默认值.
     */
    private static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 每页显示记录数 默认值 默认查全部.
     */
    private static final int DEFAULT_PAGE_SIZE = Integer.MAX_VALUE;

    private PageUtils() {
    }

    /**
     * 构建 plus 分页对象.
     *
     * @param <T> domain 实体
     * @param <K> vo 实体
     * @return 分页对象
     */
    public static <T, K> PagePlus<T, K> buildPagePlus() {
        Integer pageNum = ServletUtils.getParameterToInt(PAGE_NUM, DEFAULT_PAGE_NUM);
        final Integer pageSize = ServletUtils.getParameterToInt(PAGE_SIZE, DEFAULT_PAGE_SIZE);
        final String orderByColumn = ServletUtils.getParameter(ORDER_BY_COLUMN);
        final String isAsc = ServletUtils.getParameter(IS_ASC);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        final PagePlus<T, K> page = new PagePlus<>(pageNum, pageSize);
        final OrderItem orderItem = buildOrderItem(orderByColumn, isAsc);
        if (orderItem != null) {
            page.addOrder(orderItem);
        }
        return page;
    }

    /**
     * 构建 MP 普通分页对象.
     *
     * @param <T> domain 实体
     * @return 分页对象
     */
    public static <T> Page<T> buildPage() {
        return buildPage(null, "null");
    }


    /**
     * 构建 MP 普通分页对象.
     *
     * @param defaultOrderByColumn 默认排序列
     * @param defaultIsAsc         默认排序方向
     * @param <T>                  实体
     * @return 分页对象
     */
    private static <T> Page<T> buildPage(
            final String defaultOrderByColumn,
            final String defaultIsAsc
    ) {
        Integer pageNum = ServletUtils.getParameterToInt(PAGE_NUM, DEFAULT_PAGE_NUM);
        final Integer pageSize = ServletUtils.getParameterToInt(PAGE_SIZE, DEFAULT_PAGE_SIZE);
        final String orderByColumn = ServletUtils.getParameter(ORDER_BY_COLUMN, defaultOrderByColumn);
        final String isAsc = ServletUtils.getParameter(IS_ASC, defaultIsAsc);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        final Page<T> page = new Page<>(pageNum, pageSize);
        final OrderItem orderItem = buildOrderItem(orderByColumn, isAsc);
        if (orderItem != null) {
            page.addOrder(orderItem);
        }
        return page;
    }

    /**
     * 获取排序对象,判断列名是否合法.
     *
     * @param orderByColumn 排序列名
     * @param isAsc         排序方向
     * @return 排序对象
     */
    private static OrderItem buildOrderItem(
            final String orderByColumn,
            final String isAsc
    ) {
        if (CharSequenceUtil.isNotBlank(orderByColumn)) {
            final String newOrderByColumn = CharSequenceUtil.toUnderlineCase(orderByColumn);
            if (ASC.equals(isAsc)) {
                return OrderItem.asc(newOrderByColumn);
            } else if (DESC.equals(isAsc)) {
                return OrderItem.desc(newOrderByColumn);
            }
        }
        return null;
    }

    /**
     * 构建plus分页结果.
     *
     * @param page 分页信息
     * @param <T>  实体
     * @param <K>  Vo
     * @return 分页结果对象
     */
    public static <T, K> PageInfo<K> buildPageInfo(final PagePlus<T, K> page) {
        return new PageInfo<>(page.getTotal(), page.getRecordsVo());
    }

    /**
     * 构建mp普通分页结果.
     *
     * @param page 分页信息
     * @param <T>  实体
     * @return 分页结果
     */
    public static <T> PageInfo<T> buildPageInfo(final Page<T> page) {
        return new PageInfo<>(page.getTotal(), page.getRecords());
    }


}
