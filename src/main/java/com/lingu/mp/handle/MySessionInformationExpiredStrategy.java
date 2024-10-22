package com.lingu.mp.handle;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;
import java.util.HashMap;

/**
 * 登陆设备超限制
 */
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    private static final Logger log = LoggerFactory.getLogger(MySessionInformationExpiredStrategy.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.error("MySessionInformationExpiredStrategy onExpiredSessionDetected");
        // 1、设置响应头信息
        HashMap<Object, Object> result = new HashMap<>();
        result.put("code", "4104");
        result.put("message", "该账号已在其他设备登录！");

        // 2、转换成json字符串
        String jsonResult = JSON.toJSONString(result);

        // 3、响应给前端
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonResult);
    }
}
