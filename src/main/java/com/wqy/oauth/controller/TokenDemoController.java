package com.wqy.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token/demo")
public class TokenDemoController {

    @Autowired
    private ConsumerTokenServices tokenServices;

    @PostMapping(value = "/revoke")
    public boolean revokeToken(@RequestParam("token") String token) {
        return tokenServices.revokeToken(token);
    }

}