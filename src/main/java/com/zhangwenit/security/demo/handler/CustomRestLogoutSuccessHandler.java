package com.zhangwenit.security.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.common.result.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 自定义退出登录响应
 * @Author ZWen
 * @Date 2019/9/25 4:15 PM
 * @Version 1.0
 **/
public class CustomRestLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ResultVO resultVO = ResultVO.buildSuccess("注销成功!");
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(resultVO));
        out.flush();
        out.close();
    }
}