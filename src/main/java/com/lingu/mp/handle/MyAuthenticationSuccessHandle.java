package com.lingu.mp.handle;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * 请求未成功
 */
public class MyAuthenticationSuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1、设置响应头信息
        HashMap<Object, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "success");
        result.put("data", authentication.getPrincipal());

        // 2、转换成json字符串
        String jsonResult = JSON.toJSONString(result);

        // 3、响应给前端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonResult);
    }
}