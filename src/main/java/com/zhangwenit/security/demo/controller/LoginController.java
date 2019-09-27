package com.zhangwenit.security.demo.controller;

import com.titan.common.result.ResultVO;
import com.zhangwenit.security.demo.dto.LoginReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 3:02 PM
 * @Version 1.0
 **/
@RestController
public class LoginController {

//    @RequestMapping("/")
//    public String index(Model model) {
//        Map<String, String> msg = new HashMap<>(4);
//        msg.put("title", "测试标题");
//        msg.put("content", "测试内容");
//        msg.put("etraInfo", "欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
//        model.addAttribute("msg", msg);
//        return "home";
//    }
//
//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/needLogin")
    public ResultVO needLogin(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return ResultVO.buildError(401, "您尚未登录，请先登录!");
    }

    /**
     * 自定义登录接口
     * todo:token机制配置post可访问
     *
     * @param request
     * @param sysUser
     * @return
     * @throws ServletException
     */
    @PostMapping("/myLogin")
    @ResponseBody
    public ResultVO myLogin(HttpServletRequest request, @RequestBody LoginReq sysUser) throws ServletException {
        if (StringUtils.isEmpty(sysUser.getUsername()) || StringUtils.isEmpty(sysUser.getPassword())) {
            return ResultVO.buildError(401, "用户名或密码为空");
        }
        request.login(sysUser.getUsername(), sysUser.getPassword());
        return ResultVO.buildSuccess(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/myLogin")
    @ResponseBody
    public ResultVO login(HttpServletRequest request, LoginReq sysUser) throws ServletException {
        if (StringUtils.isEmpty(sysUser.getUsername()) || StringUtils.isEmpty(sysUser.getPassword())) {
            return ResultVO.buildError(401, "用户名或密码为空");
        }
        request.login(sysUser.getUsername(), sysUser.getPassword());
        return ResultVO.buildSuccess(SecurityContextHolder.getContext().getAuthentication());
    }

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