package com.lingu.mp.config.security;

import com.lingu.mp.filter.JsonUsernamePasswordAuthenticationFilter;
import com.lingu.mp.handle.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


/**
 * spring security 配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
    @Bean
    public UserDetailsService userDetailsService() {
        // 实现UserDetailsService接口的类
        return new MyDBUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider()));
    }


    /**
     * 过滤器链
     */
    @Bean
    public SecurityFilterChain secuityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)      // 关闭csrf防御
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth        // 授权管理
                        .requestMatchers("/student").hasAuthority("student")
                        .requestMatchers("/teacher").hasAuthority("teacher")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception       // 异常请求处理
                        // .authenticationEntryPoint(new MyAuthenticationEntryHandle())  // 未认证处理器，（默认的是重定向到login）
                        .accessDeniedHandler(new MyAccessDeniedHandle())  // 未授权处理器
                        )
                .sessionManagement(session -> session       // 会话并发管理
                        .maximumSessions(1)  // 允许的最多登陆设备（会话窗口）
                        .expiredSessionStrategy(new MySessionInformationExpiredStrategy())      // 登陆设备超限处理器
                        )
                .formLogin(formLogin -> formLogin       // 表单登录
                        .loginProcessingUrl("/form/login")     // 自定义请求路径
                        .successHandler(new MyAuthenticationSuccessHandle())
                        .failureHandler(new MyAuthenticationFailureHandle())
                )
                // 自定义登录 以支持json登录
                .addFilterAt(
                        new JsonUsernamePasswordAuthenticationFilter(
                                authenticationManager()
                        ),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
