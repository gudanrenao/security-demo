package com.zhangwenit.security.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

/**
 * spring security demo
 *
 * @author ze
 * 关键Filter:
 * @see org.springframework.security.web.context.SecurityContextPersistenceFilter 请求来临时，创建SecurityContext安全上下文信息，请求结束时清空SecurityContextHolder
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter 调用身份认证器，校验身份 {@link UsernamePasswordAuthenticationToken}
 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter 上面的Filter的父类
 * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter
 * @see org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter
 * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter 匿名认证过滤器：其他认证过滤器执行后执行
 * @see org.springframework.security.web.access.ExceptionTranslationFilter 异常转换过滤器 {@link AuthenticationException}{@link AccessDeniedException}
 * @see org.springframework.security.web.access.intercept.FilterSecurityInterceptor
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zhangwenit.security.demo.**.mapper"})
public class SecurityDemoApplication {

    /**
     * todo:修改了接口权限或数据权限如何使SpringSession失效
     * todo: 单点登录(同时登录个数限制)
     * todo:post CSRF token？
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
