package com.lingu.mp.handle;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * 请求未失败
 */
public class MyAuthenticationFailureHandle implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(MyAuthenticationFailureHandle.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("MyAuthenticationFailureHandle onAuthenticationFailure");
        // 1、设置响应头信息
        HashMap<Object, Object> result = new HashMap<>();
        result.put("code", "4102");
        result.put("message", exception.getMessage());
        result.put("data", null);

        // 2、转换成json字符串
        String jsonResult = JSON.toJSONString(result);

        // 3、响应给前端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonResult);
    }
}
