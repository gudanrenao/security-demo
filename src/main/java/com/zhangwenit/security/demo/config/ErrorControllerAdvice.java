package com.zhangwenit.security.demo.config;

import com.titan.common.result.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName ErrorControllerAdvice
 * @Description 统一异常处理
 * @Author ZWen
 * @Date 2018/11/7 4:59 PM
 * @Version 1.0
 **/
@RestControllerAdvice
public class ErrorControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(value = RuntimeException.class)
    public ResultVO handle(RuntimeException e) {
        logger.error("Handle RuntimeException", e);
        return new ResultVO(-1, e.getMessage());
    }
}