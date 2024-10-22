package com.lingu.mp.controller;

import com.lingu.mp.pojo.Result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {
    @GetMapping
    public Result<String> getTeacher(){
        return Result.success("Nice to meet you, my job is teacher!");
    }
}
