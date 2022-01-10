package com.mall.common.core.mybatisplus.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.mall.common.constant.GlobalConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分页增强.
 *
 * @param <T> 实体类
 * @param <V> Vo视图类
 * @author 钟舒艺
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@SuppressWarnings({"unused"})
public class PagePlus<T, V> implements IPage<T> {

    private static final long serialVersionUID = 7467570203500509160L;


    /**
     * domain实体列表.
     */
    private transient List<T> records = Collections.emptyList();

    /**
     * vo实体列表.
     */
    private transient List<V> recordsVo = Collections.emptyList();

    /**
     * 总数.
     */
    private long total = 0L;

    /**
     * 页长度.
     */
    private long size = GlobalConstants.DEFAULT_PAGE_SIZE;

    /**
     * 当前页.
     */
    private long current = GlobalConstants.SUPER_ADMIN_ROLE_ID;

    /**
     * 排序字段信息.
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL.
     */
    private boolean optimizeCountSql = true;

    /**
     * 是否进行 count 查询.
     */
    private boolean isSearchCount = true;

    /**
     * 是否命中count缓存.
     */
    private boolean hitCount = false;

    /**
     * countId.
     */
    private String countId;

    /**
     * 最大limit.
     */
    private Long maxLimit;

    /**
     * PagePlus构造函数.
     *
     * @param current 当前页
     * @param size    每页数量
     */
    public PagePlus(final long current, final long size) {
        this(current, size, 0L);
    }

    /**
     * PagePlus构造函数.
     *
     * @param current 当前页
     * @param size    每页数量
     * @param total   总条数
     */
    private PagePlus(final long current, final long size, final long total) {
        this(current, size, total, true);
    }

    /**
     * PagePlus构造函数.
     *
     * @param current       当前页
     * @param size          每页数量
     * @param isSearchCount 是否进行 count 查询.
     */
    public PagePlus(final long current, final long size, final boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    /**
     * 构造方法.
     *
     * @param current       当前页
     * @param size          分页大小
     * @param total         总条数
     * @param isSearchCount 是否进行count查询
     */
    private PagePlus(
            final Long current, final long size, final long total, final boolean isSearchCount
    ) {
        if (current > 1L) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    @Override
    public final List<OrderItem> orders() {
        return this.orders;
    }

    /**
     * 增加排序规则.
     *
     * @param items 排序规则
     */
    public void addOrder(final OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
    }

    /**
     * 增加排序规则.
     *
     * @param items 排序规则list
     */
    public void addOrder(final List<OrderItem> items) {
        this.orders.addAll(items);
    }

    @Override
    public final List<T> getRecords() {
        return this.records;
    }

    @Override
    public final IPage<T> setRecords(final List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public final long getTotal() {
        return this.total;
    }

    @Override
    public final IPage<T> setTotal(final long total) {
        this.total = total;
        return this;
    }

    @Override
    public final long getSize() {
        return this.size;
    }

    @Override
    public final IPage<T> setSize(final long size) {
        this.size = size;
        return this;
    }

    @Override
    public final long getCurrent() {
        return this.current;
    }

    @Override
    public final IPage<T> setCurrent(final long current) {
        this.current = current;
        return this;
    }

    @Override
    public final boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    @Override
    public final long getPages() {
        return IPage.super.getPages();
    }
}
