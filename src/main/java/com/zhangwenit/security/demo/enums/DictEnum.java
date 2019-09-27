package com.zhangwenit.security.demo.enums;

/**
 * @author zw
 */
public class DictEnum {

    /**
     * 分页查询时排序规则
     */
    public static class SortOrder {
        public static final String ASC = "asc";//升序
        public static final String DESC = "desc";//降序
    }

    /**
     * 账号类型
     */
    public static class SysUserType {
        public static final int MANAGER = 1; //超级管理员
        public static final int COMMON = 2; //普通账号
    }

    /**
     * 账号是否可用
     */
    public static class SysUserEnabled {
        public static final int YES = 1; //可用
        public static final int NO = 0; //被禁用
    }


}
