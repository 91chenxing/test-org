package com.chenxing;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
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
    public String login(@RequestParam(name = "return_to", required = false) String returnTo,
                        @RequestParam(name = "email", required = false, defaultValue = "licx@easemob.com") String email) throws UnsupportedEncodingException {
        String jwt = JWT.create()
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("email", email)
                .withClaim("name", "小星星")
                .sign(Algorithm.HMAC256(JWT_SECRET));
        log.info("/zendesk/sso/login jwt:{} return_to:{} email:{}", returnTo, jwt, email);
        return "redirect:" + "https://chenxing.zendesk.com/access/jwt?jwt=" + jwt + "&return_to=" + returnTo;
    }

    @RequestMapping("/zendesk/sso/logout")
    public @ResponseBody Map<String, Object> logout(@RequestParam(name = "return_to", required = false) String returnTo) {
        log.info("/zendesk/sso?return_to={}", returnTo);
        Map<String, Object> result = new HashMap<>();
        result.put("return_to", returnTo);
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String token = JWT.create()
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("email", "licx@easemob.com")
                .withClaim("name", "小星星")
                .sign(Algorithm.HMAC256(JWT_SECRET));
        System.out.println(token);
        JWT decode = JWT.decode(token + "1");
        System.out.println(decode.getClaim("email").asString());
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT verify = verifier.verify(token + "1");
        System.out.println(verify.getClaim("email").asString());

    }
}
