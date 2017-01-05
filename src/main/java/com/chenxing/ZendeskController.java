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
 * Created by lichenxing on 2017/1/6.
 */
@Controller
@Slf4j
public class ZendeskController {

    private static final String JWT_SECRET = "omMTXKjpaPpiK5UgzQqX1g5PTH24BABdSqc0XTg5A5MWNdwa";

    @RequestMapping("/zendesk/sso")
    public @ResponseBody Map<String, Object> loginPage(@RequestParam(name = "return_to", required = false) String returnTo) {
        log.info("/zendesk/sso return_to:{}", returnTo);
        Map<String, Object> result = new HashMap<>();
        result.put("return_to", returnTo);
        return result;
    }

    @RequestMapping("/zendesk/sso/login")
    public String login(@RequestParam(name = "return_to", required = false) String returnTo) throws UnsupportedEncodingException {
        String jwt = JWT.create()
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("eamil", "licx@easemob.com")
                .withClaim("name", "小星星")
                .sign(Algorithm.HMAC256(JWT_SECRET));
        log.info("/zendesk/sso/login jwt:{} return_to:{}", returnTo, jwt);
        return "redirect:" + "https://chenxing.zendesk.com/access/jwt?jwt=" + jwt + "&return_to=" + returnTo;
    }

    @RequestMapping("/zendesk/sso/logout")
    public Map<String, Object> logout(@RequestParam(name = "return_to", required = false) String returnTo) {
        log.info("/zendesk/sso?return_to={}", returnTo);
        Map<String, Object> result = new HashMap<>();
        result.put("return_to", result);
        return result;
    }
}
