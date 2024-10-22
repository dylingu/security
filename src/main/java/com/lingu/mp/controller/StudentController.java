package com.lingu.mp.controller;

import com.lingu.mp.pojo.Result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @GetMapping
    public Result<String> getStudent(){
        return Result.success("Hello, I'm a Student!");
    }
}
