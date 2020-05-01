package com.base.leran.async.controller;

import com.base.leran.async.service.AsyncService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {
    @Autowired
    private AsyncService asyncService;

    @GetMapping("/wait")
    @ApiOperation("同步等待")
    public String wait1() {
        asyncService.wait1();
        return "success";
    }

    @GetMapping("/sync")
    @ApiOperation("异步等待")
    public String sync1() {
        asyncService.sync1();
        return "success";
    }
}
