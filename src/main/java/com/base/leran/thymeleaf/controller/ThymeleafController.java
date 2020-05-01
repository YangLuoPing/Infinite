package com.base.leran.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ThymeleafController {
    @RequestMapping("/test1")
    public String test(Model model) {
        model.addAttribute("msg", "消息");
        Set<String> allNames = new HashSet<String>();
        List<Integer> allIds = new ArrayList<Integer>();
        for (int x = 0; x < 5; x++) {
            allNames.add("boot-" + x);
            allIds.add(x);
        }
        model.addAttribute("names", allNames);
        model.addAttribute("ids", allIds);
        model.addAttribute("mydate", new java.util.Date());
        return "learn/thymeleaf/demo01";
    }
}
