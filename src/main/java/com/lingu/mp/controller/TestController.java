package com.lingu.mp.controller;

import com.lingu.mp.pojo.DO.User;
import com.lingu.mp.pojo.Result.Result;
import com.lingu.mp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/gets")
    public Result<Principal> test2() {
        log.info("获取登录用户信息");
        return Result.success(SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping("/add")
    public Result<String> test3(@RequestBody User user) {
        log.info("添加用户：{}", user.getUsername());
        userService.saveDetailUser(user);
        return Result.success();
    }
}
