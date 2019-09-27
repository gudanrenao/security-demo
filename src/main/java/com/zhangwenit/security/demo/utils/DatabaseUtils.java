package com.zhangwenit.security.demo.utils;

import com.titan.common.enums.ResultCode;
import com.zhangwenit.security.demo.exception.DatabaseInsertOrUpdateException;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 12:06 PM
 * @Version 1.0
 **/
public class DatabaseUtils {

    public static void checkModifyOne(int update) {
        if (update < 1) {
            throw new DatabaseInsertOrUpdateException(ResultCode.UPDATE_OR_INSERT_ERROR);
        }
    }
}