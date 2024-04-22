package com.eminence.footballmatch.controller;


import com.eminence.footballmatch.dto.JwtTokenRequest;
import com.eminence.footballmatch.service.FootballMatchService;
import com.eminence.footballmatch.service.JWTDecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/football")
public class FootBallMatchController {

    private FootballMatchService footballMatchService;


    @Autowired
    public FootBallMatchController(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }


    @GetMapping("/fetch-draw-matches/{year}")
    public ResponseEntity<Map<String, Object>> fetchDrawMatches(@PathVariable String year){

        Map<String, Object> response=footballMatchService.fetchDrawMatches(year);

        return ResponseEntity.ok(response);
    }

}
