package com.zhangwenit.security.demo.controller;

import com.titan.common.result.ResultVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 3:02 PM
 * @Version 1.0
 **/
@RestController
public class LoginController {

    @GetMapping("/needLogin")
    public ResultVO needLogin(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return ResultVO.buildError(401, "您尚未登录，请先登录!");
    }

//    /**
//     * 自定义登录接口
//     * todo:token机制配置post可访问
//     *
//     * @param request
//     * @param sysUser
//     * @return
//     * @throws ServletException
//     */
//    @PostMapping("/myLogin")
//    @ResponseBody
//    public ResultVO myLogin(HttpServletRequest request, @RequestBody LoginReq sysUser) throws ServletException {
//        if (StringUtils.isEmpty(sysUser.getUsername()) || StringUtils.isEmpty(sysUser.getPassword())) {
//            return ResultVO.buildError(401, "用户名或密码为空");
//        }
//        request.login(sysUser.getUsername(), sysUser.getPassword());
//        return ResultVO.buildSuccess(SecurityContextHolder.getContext().getAuthentication());
//    }

//
//    @GetMapping("/logout")
//    public  String logout(){
//        return "logout";
//    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "456";
        System.out.println("encode password is :" + passwordEncoder.encode(password));
    }
}