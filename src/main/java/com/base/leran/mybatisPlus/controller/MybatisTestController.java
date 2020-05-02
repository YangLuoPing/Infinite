package com.base.leran.mybatisPlus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;
import com.base.leran.mybatisPlus.service.PfOrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mybarisPlusTest")
@Api(tags = {"MybarisPlus 测试"})
public class MybatisTestController {
    @Autowired
    private PfOrgUserService pfOrgUserService;

    @ApiOperation("验证非必须参数")
    @GetMapping(value = {"/list/{page}/{size}/{name}", "/list/{size}/{name}"})
    public List<PfOrgUser> pageTest(@ApiParam("页码") @PathVariable(value = "page", required = false) Integer page,
                                    @ApiParam("页大小") @PathVariable(value = "size") Integer size,
                                    @ApiParam("姓名参数") @PathParam(value = "name") String name// 参数


    ) {

        Page<PfOrgUser> pageable = new Page<>(page == null ? 0 : page, size == null ? 0 : size);
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("name", name == null ? "测试2" : name);
        queryWrapper.like("name", name == null ? "" : name);

        List<PfOrgUser> list = pfOrgUserService.findAllPage(pageable, queryWrapper);
        return list;
    }

    @ApiOperation("接受参数测试")
    @PostMapping("/update")
    public String update(@ApiParam("表单参数") @RequestBody() Map<String, Object> requestBody, // form 表单数据
                         @ApiParam("header参数") @RequestHeader Map<String, Object> requestHeader, // header数据
                         @ApiParam("cookies") @CookieValue() String cookies // cookies
    ) {
        System.out.println("requestBody  sex:" + requestBody.get("sex"));
        return "sucess";
    }

}
