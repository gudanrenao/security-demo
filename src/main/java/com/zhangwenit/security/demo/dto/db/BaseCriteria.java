package com.zhangwenit.security.demo.dto.db;

import com.zhangwenit.security.demo.enums.DictEnum;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Description 通用查询字段
 * @Author ZWen
 * @Date 2019/5/17 4:30 PM
 * @Version 1.0
 **/
public class BaseCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 分页最大显示行数
     */
    private static final int MAX_PAGE_SIZE = 100;
    /**
     * 每天的起始时分秒
     */
    protected static final String DAY_START_TIME = " 00:00:00";
    /**
     * 每天的结束时分秒
     */
    protected static final String DAY_END_TIME = " 23:59:59";

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "每页显示行数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序规则，默认倒序", example = "desc")
    private String sortOrder = DictEnum.SortOrder.DESC;

    @ApiModelProperty(value = "排序字段", example = "createTime")
    private String sortField = "createTime";

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * 分页参数及排序校验
     */
    public void checkParams() {
        if (this.currentPage < 1) {
            throw new IllegalArgumentException("currentPage must large than 0");
        }
        if (this.pageSize < 1 || this.pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("pageSize must large than 0 and small than 100");
        }
        if (StringUtils.isEmpty(this.sortField)) {
            //排序字段如果为空，设置为创建时间
            this.sortField = "createTime";
        }
        if (StringUtils.isEmpty(this.sortOrder)) {
            this.sortOrder = "desc";
        }
    }

    @Override
    public String toString() {
        return "BaseCriteria{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", sortOrder='" + sortOrder + '\'' +
                ", sortField='" + sortField + '\'' +
                '}';
    }
}