package com.zhangwenit.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/26 1:38 PM
 * @Version 1.0
 **/
@Configuration
public class BaseWebConfig implements WebMvcConfigurer {

    /**
     * 线上如果已经通过 NGINX 配置跨域，不可以重复配置，否则前台会报错(多个一样的请求头异常)
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
//                //允许前端拿到的headers
//                .exposedHeaders("Authorization", "Access-Control-Allow-Origin","x-access-token")
//                //跨域允许时间
//                .maxAge(3600);
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true).allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Max-Age",
                        "X-Frame-Options", "x-access-token", "Authorization")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .maxAge(7200);
    }
}