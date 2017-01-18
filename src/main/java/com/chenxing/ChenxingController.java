package com.chenxing;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ChenxingController
 *
 * @author Chenxing Li
 * @date 2017/1/18 17:33
 */

@Controller
@Slf4j
public class ChenxingController {

    private static final String JWT_SECRET = "123456";

    @RequestMapping("/chenxing/sso")
    public @ResponseBody
    Map<String, Object> loginPage(@RequestParam(name = "return_to", required = false) String returnTo) {
        log.info("/chenxing/sso return_to:{}", returnTo);
        Map<String, Object> result = new HashMap<>();
        result.put("return_to", returnTo);
        return result;
    }

    @RequestMapping("/chenxing/sso/login")
    public String login(@RequestParam(name = "return_to", required = false) String returnTo,
                        @RequestParam(name = "email", required = false, defaultValue = "licx@easemob.com") String email) throws UnsupportedEncodingException {
        String jwt = JWT.create()
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("email", email)
                .withClaim("name", "小星星")
                .sign(Algorithm.HMAC256(JWT_SECRET));
        log.info("/chenxing/sso/login jwt:{} return_to:{} email:{}", returnTo, jwt, email);
        return "redirect:" + "http://lee.kefu.chenxing.com:8080/v1/access/jwt?jwt=" + jwt + "&return_to=" + returnTo;
    }

    @RequestMapping("/chenxing/sso/logout")
    public @ResponseBody Map<String, Object> logout(@RequestParam(name = "return_to", required = false) String returnTo) {
        log.info("/chenxing/sso?return_to={}", returnTo);
        Map<String, Object> result = new HashMap<>();
        result.put("return_to", returnTo);
        return result;
    }

}
