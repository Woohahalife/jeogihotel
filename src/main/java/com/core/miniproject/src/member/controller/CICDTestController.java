package com.core.miniproject.src.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public-api")
public class CICDTestController {

    @GetMapping("/cicd-test")
    public String cicdTest() {
        return "cicdTestOK";
    }
}
