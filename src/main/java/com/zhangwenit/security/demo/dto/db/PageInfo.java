package com.zhangwenit.security.demo.dto.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import com.zhangwenit.security.demo.enums.DictEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Description 分页数据
 * @Author ZWen
 * @Date 2019/5/17 7:01 PM
 * @Version 1.0
 **/
@JsonIgnoreProperties({"pages", "searchCount"})
public class PageInfo<T> implements IPage<T> {

    private static final long serialVersionUID = 8545996863226528798L;
    /**
     * 查询数据列表
     */
    @ApiModelProperty("列表数据")
    @JsonProperty("resultList")
    private List<T> records;
    @ApiModelProperty("总行数")
    @JsonProperty("totalCount")
    private long total;
    /**
     * 每页显示条数，默认 10
     */
    @JsonIgnore
    private long size;
    /**
     * 当前页
     */
    @JsonIgnore
    private long current;
    /**
     * SQL 排序 ASC 数组
     */
    @JsonIgnore
    private List<OrderItem> orders;
    /**
     * 自动优化 COUNT SQL
     */
    @JsonIgnore
    private boolean optimizeCountSql;
    /**
     * 是否进行 count 查询
     */
    @JsonIgnore
    private boolean isSearchCount;

    public PageInfo() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.isSearchCount = true;
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageInfo(long current, long size) {
        this(current, size, 0L);
    }

    /**
     * 分页及排序构造
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageInfo(long current, long size, BaseCriteria criteria) {
        this(current, size, 0);
        if (criteria.getSortOrder().equals(DictEnum.SortOrder.ASC)) {
            addOrder(OrderItem.asc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, criteria.getSortField())));
        } else {
            addOrder(OrderItem.desc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, criteria.getSortField())));
        }
    }


    public PageInfo(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageInfo(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    public PageInfo(long current, long size, long total, boolean isSearchCount) {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList();
        this.optimizeCountSql = true;
        this.isSearchCount = true;
        if (current > 1L) {
            this.current = current;
        }

        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1L;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public PageInfo<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public PageInfo<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public PageInfo<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public PageInfo<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList(this.orders.size());
        this.orders.forEach((i) -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }

        });
        return (String[]) columns.toArray(new String[0]);
    }

    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = this.orders.size() - 1; i >= 0; --i) {
            if (filter.test(this.orders.get(i))) {
                this.orders.remove(i);
            }
        }

    }

    public PageInfo<T> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public PageInfo<T> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.getOrders();
    }

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        return this.total < 0L ? false : this.isSearchCount;
    }

    public PageInfo<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PageInfo<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }
}