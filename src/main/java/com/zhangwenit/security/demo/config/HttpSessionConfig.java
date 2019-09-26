package com.zhangwenit.security.demo.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis spring session
 *
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 7:20 PM
 * @Version 1.0
 **/
@EnableRedisHttpSession(
        //redis中的命名空间
        redisNamespace = "IH:MALL:SESSION",
        //有效期，1小时
        maxInactiveIntervalInSeconds = 3600
)
public class HttpSessionConfig {
}