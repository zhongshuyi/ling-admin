package com.mall.common.core.mybatisplus.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页增强
 *
 * @param <T> 实体类
 * @param <V> Vo视图类
 * @author 钟舒艺
 * @date 2021-10-29-22:10
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class PagePlus<T, V> implements IPage<T> {

    private static final long serialVersionUID = 7467570203500509160L;


    /**
     * domain实体列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * vo实体列表
     */
    private List<V> recordsVo = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0L;

    /**
     * 页长度
     */
    private long size = 10L;

    /**
     * 当前页
     */
    private long current = 1L;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;

    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    /**
     * 是否命中count缓存
     */
    private boolean hitCount = false;

    /**
     * countId
     */
    private String countId;

    /**
     * 最大limit
     */
    private Long maxLimit;


    public PagePlus() {
    }

    public PagePlus(long current, long size) {
        this(current, size, 0L);
    }

    public PagePlus(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PagePlus(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    /**
     * 构造方法
     *
     * @param current       当前页
     * @param size          分页大小
     * @param total         总条数
     * @param isSearchCount 是否进行count查询
     */
    public PagePlus(long current, long size, long total, boolean isSearchCount) {
        if (current > 1L) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public static <T, V> PagePlus<T, V> of(long current, long size) {
        return of(current, size, 0);
    }

    public static <T, V> PagePlus<T, V> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T, V> PagePlus<T, V> of(long current, long size, boolean searchCount) {
        return of(current, size, 0, searchCount);
    }

    public static <T, V> PagePlus<T, V> of(
            long current, long size, long total, boolean searchCount) {
        return new PagePlus<>(current, size, total, searchCount);
    }

    @Override
    public List<OrderItem> orders() {
        return this.orders;
    }

    /**
     * 增加排序规则
     *
     * @param items 排序规则
     */
    public void addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
    }

    /**
     * 增加排序规则
     *
     * @param items 排序规则list
     */
    public void addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    @Override
    public long getPages() {
        return IPage.super.getPages();
    }


}
