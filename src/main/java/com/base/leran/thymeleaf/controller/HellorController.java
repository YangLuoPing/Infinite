package com.base.leran.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellorController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world!";
    }

}
