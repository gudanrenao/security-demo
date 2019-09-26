package com.zhangwenit.security.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.titan.common.result.ResultVO;
import com.zhangwenit.security.demo.dto.CustomUserDetails;
import com.zhangwenit.security.demo.utils.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 登录成功后返回的信息
 * @Author ZWen
 * @Date 2019/9/25 3:57 PM
 * @Version 1.0
 **/
public class CustomRestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ResultVO<CustomUserDetails> resultVO = ResultVO.buildSuccess(SecurityUtils.getUserDetails());
        resultVO.setErrMsg("登录成功");
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(resultVO));
        out.flush();
        out.close();
    }
}