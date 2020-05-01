package com.base.system.monitor.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api("Druid监控")
public class DruidController {
    @GetMapping(value = {"/druid/index"})
    public String toIndex() {
        return "redirect:druid/index.html";
    }
    @GetMapping(value = {"/druid/login"})
    public String toLogin() {
        return "redirect:druid/login.html";
    }
}
