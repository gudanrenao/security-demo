package com.zhangwenit.security.demo.exception;

import com.titan.common.enums.ResultCode;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @Description 统一自定义异常
 * @Author ZWen
 * @Date 2019/4/28 5:57 PM
 * @Version 1.0
 **/
public class DatabaseInsertOrUpdateException extends RuntimeException {

    private static final long serialVersionUID = 6409874889208817528L;
    private final ResultCode resultCode;
    private Integer errCode;
    private String message;

    public DatabaseInsertOrUpdateException(ResultCode resultCode, Object... params) {
        this.resultCode = resultCode;
        if (ArrayUtils.isEmpty(params)) {
            this.message = resultCode.getMessage();
        } else {
            this.message = String.format(resultCode.getMessage(), params);
        }
    }

    public DatabaseInsertOrUpdateException(String message) {
        this.resultCode = null;
        this.message = message;
    }

    public DatabaseInsertOrUpdateException(Integer errCode, String message) {
        this.resultCode = null;
        this.errCode = errCode;
        this.message = message;
    }

    public int getResultCode() {
        return this.errCode == null ? (this.resultCode == null ? -1 : this.resultCode.getIndex()) : this.errCode;
    }

    @Override
    public String getMessage() {
        if (this.resultCode == null) {
            return this.getMsg();
        } else {
            return "code: " +
                    this.resultCode.getIndex() +
                    "; message: " +
                    this.getMsg();
        }
    }

    public String getMsg() {
        return this.message;
    }

    public Integer getErrCode() {
        return errCode;
    }
}