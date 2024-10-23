package com.lingu.mp.handle;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

/**
 * 请求未认证
 */
public class MyAuthenticationEntryHandle implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(MyAuthenticationEntryHandle.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("MyAuthenticationEntryHandle commence");
        // 1、设置响应头信息
        HashMap<Object, Object> result = new HashMap<>();
        result.put("code", "4103");
        result.put("message", authException.getMessage());

        // 2、转换成json字符串
        String jsonResult = JSON.toJSONString(result);

        // 3、响应给前端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonResult);
    }
}
