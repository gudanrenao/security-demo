//package com.zhangwenit.security.demo.service;
//
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//
//import javax.servlet.*;
//import java.io.IOException;
//
///**
// * todo:需要仔细分析源码
// * 其实可以不用自定义，自定义下面两个完成权限校验就可以：
// *
// * @author zw
// * @see org.springframework.security.access.AccessDecisionManager  权限匹配检验
// * @see SecurityMetadataSource 获取当前请求所需要的角色或权限
// */
//public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
//
//    private FilterInvocationSecurityMetadataSource securityMetadataSource;
//
//    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
//        super.setAccessDecisionManager(myAccessDecisionManager);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        FilterInvocation fi = new FilterInvocation(request, response, chain);
//        invoke(fi);
//    }
//
//    public void invoke(FilterInvocation fi) throws IOException, ServletException {
//        //fi里面有一个被拦截的url
//        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
//        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
//        InterceptorStatusToken token = super.beforeInvocation(fi);
//        try {
//            //执行下一个拦截器
//            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//        } finally {
//            super.afterInvocation(token, null);
//        }
//    }
//
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        return this.securityMetadataSource;
//    }
//}
