package com.zhangwenit.security.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/29 1:58 PM
 * @Version 1.0
 **/
public class MyCsrfFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MyCsrfFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.debug("refer : [{}]", httpServletRequest.getHeader("referer"));
        chain.doFilter(request, response);
    }
}