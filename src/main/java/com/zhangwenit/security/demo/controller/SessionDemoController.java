package com.zhangwenit.security.demo.controller;

import com.titan.common.result.ResultVO;
import com.zhangwenit.security.demo.service.CustomUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 11:32 AM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/session")
public class SessionDemoController {

    private final CustomUserService customUserService;

    public SessionDemoController(CustomUserService customUserService) {
        this.customUserService = customUserService;

    }

    @GetMapping("/test/cookie")
    public String cookie(@RequestParam("browser") String browser, HttpServletRequest request, HttpSession session) {
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在session，设置browser=" + browser);
            session.setAttribute("browser", browser);
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return "index";
    }

    /**
     * TODO：如何在禁用账户后使对应账号的session失效
     *
     * @param username
     * @return
     */
    @GetMapping("delByUsername")
    public ResultVO delSessionByUserId(@RequestParam String username) {
//        UserDetails userDetails = customUserService.loadUserByUsername(username);
//        List<SessionInformation> allSessions = sessionRegistry.getAllSessions(userDetails, true);
//        if (!CollectionUtils.isEmpty(allSessions)) {
//            for (SessionInformation sessionInformation : allSessions) {
//                sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
//            }
//            return ResultVO.buildSuccess(allSessions);
//        }
        return ResultVO.buildSuccess();
    }
}