package com.chenxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lichenxing on 2017/1/5.
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/t1")
    public String t1(HttpServletResponse response) {
        log.info("/test/t1");
        Cookie cookie = new Cookie("myCookie", "hahaha");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1000000);
        response.addCookie(cookie);
        return "redirect:http://localhost:8080";
    }

    @RequestMapping("/t2")
    public @ResponseBody String t2() {
        log.info("/test/t2");
        return "redirect:/test/t2";
    }
}
