package com.zhangwenit.security.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.common.result.ResultVO;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 登录失败后返回的信息处理
 * @Author ZWen
 * @Date 2019/9/25 4:11 PM
 * @Version 1.0
 **/
public class CustomRestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * Called when an authentication attempt fails.
     *
     * @param request  the request during which the authentication attempt occurred.
     * @param response the response.
     * @param e        the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ResultVO resultVO = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            resultVO = ResultVO.buildError(-1, "账户名或者密码输入错误!");
        } else if (e instanceof LockedException) {
            resultVO = ResultVO.buildError(-1, "账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            resultVO = ResultVO.buildError(-1, "密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            resultVO = ResultVO.buildError(-1, "账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            resultVO = ResultVO.buildError(-1, "账户被禁用，请联系管理员!");
        } else {
            resultVO = ResultVO.buildError(-1, "登录失败!");
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(resultVO));
        out.flush();
        out.close();
    }
}