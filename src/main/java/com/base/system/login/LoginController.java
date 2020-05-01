package com.base.system.login;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ApiOperation("登陆相关API")
public class LoginController {

    @GetMapping(value = {"/", "/index"})
    public String toIndex() {
        return "redirect:views/index.html";
    }

    @GetMapping("/toLogin")
    public String toLogin() {

        return "redirect:views/user/login.html";
    }

}
