package com.eminence.footballmatch.controller;

import com.eminence.footballmatch.service.JWTDecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TokenDecodeController {


    private JWTDecodeService jwtDecodeService;

    @Autowired
    public TokenDecodeController(JWTDecodeService jwtDecodeService) {
        this.jwtDecodeService = jwtDecodeService;
    }

    @GetMapping("/token-decode/{jwtTokenRequest}")
    public String decodeJwt(@PathVariable String jwtTokenRequest) {
        return jwtDecodeService.decodeJwt(jwtTokenRequest);
    }
}
