package com.zhangwenit.security.demo.config;

import com.zhangwenit.security.demo.handler.CustomRestAuthenticationFailureHandler;
import com.zhangwenit.security.demo.handler.CustomRestAuthenticationSuccessHandler;
import com.zhangwenit.security.demo.handler.CustomRestLogoutSuccessHandler;
import com.zhangwenit.security.demo.mapper.SysPermissionMapper;
import com.zhangwenit.security.demo.service.MyAccessDecisionManager;
import com.zhangwenit.security.demo.service.MyInvocationSecurityMetadataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author by ZWen on 19/9/18.
 */
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService customUserService;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

//    /**
//     * 添加自定义AuthenticationProvider
//     *
//     * @return
//     */
//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserService);
//        authenticationProvider.setPasswordEncoder(new MyPasswordEncoder());
//        return authenticationProvider;
//    }

    @Bean
    public AccessDecisionManager myAccessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource() {
        return new MyInvocationSecurityMetadataSourceService(sysPermissionMapper);
    }

//    /**
//     * 该方法+http.addFilterBefore = withObjectPostProcessor
//     * @return
//     */
//    @Bean
//    public FilterSecurityInterceptor filterSecurityInterceptor() {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
//        filterSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager());
//        return filterSecurityInterceptor;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user Details Service验证
        auth.userDetailsService(customUserService)
                //todo:密码加密并加盐 原理
                .passwordEncoder(new BCryptPasswordEncoder());
//        auth.authenticationProvider(customAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //withObjectPostProcessor 用来修改或者替代通过Java方式配置创建的对象实例
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
                        o.setAccessDecisionManager(myAccessDecisionManager());
                        return o;
                    }
                })
                //发开无需拦截的请求地址
                .antMatchers("/myLogin").permitAll()
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                //自定义登录POST请求地址 todo:postman怎么访问
                .loginProcessingUrl("/login")
                .loginPage("/needLogin")
                .defaultSuccessUrl("/")
                .failureUrl("/needLogin")
                //自定义登录成功rest响应
                .successHandler(new CustomRestAuthenticationSuccessHandler())
                //自定义登录失败rest响应
                .failureHandler(new CustomRestAuthenticationFailureHandler())
                //登录页面用户任意访问
                .permitAll()
                .and()
                //注销行为任意访问
                .logout()
                //退出登录地址
                .logoutUrl("/logout")
                //退出登录成功响应
                .logoutSuccessHandler(new CustomRestLogoutSuccessHandler())
                .permitAll()
                //禁用csrf 不推荐
                .and().csrf().disable()
                //todo：如何在redis管理session时做并发登录控制
//                .sessionManagement().maximumSessions(1)

        ;
//        http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}

