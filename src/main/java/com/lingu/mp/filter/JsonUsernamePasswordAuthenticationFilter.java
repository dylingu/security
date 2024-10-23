package com.lingu.mp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingu.mp.handle.MyAuthenticationFailureHandle;
import com.lingu.mp.handle.MyAuthenticationSuccessHandle;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandle());
        setAuthenticationFailureHandler(new MyAuthenticationFailureHandle());
        setFilterProcessesUrl("/json/login");      // 自定义请求路径
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 判断是否为POST请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("不支持的请求方式： " + request.getMethod());
        }

        // 判断是否为json格式
        if (request.getContentType().equalsIgnoreCase("application/json")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map credentials = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = (String) credentials.get(USER_NAME);
                String password = (String) credentials.get(PASSWORD);
                if (username == null || password == null) {
                    throw new AuthenticationServiceException("用户名和密码为空！");
                }
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new AuthenticationServiceException("无法转换成JSON！", e);
            }
        } else {
            // 调用父类的方法处理
            return super.attemptAuthentication(request, response);
        }
    }
}
