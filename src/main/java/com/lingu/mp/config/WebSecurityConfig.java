package com.lingu.mp.config;

import com.lingu.mp.filter.MyUsernamePasswordAuthenticationFilter;
import com.lingu.mp.handle.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * spring security 配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain secuityFilterChain(HttpSecurity http) throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        filter.setFilterProcessesUrl("/login");

        // 授权管理
        http.authorizeHttpRequests(
                auth -> auth    // (一定要按顺序)
                        .requestMatchers("/student").hasRole("student")
                        .requestMatchers("/teacher").hasRole("teacher")
                        .anyRequest().authenticated()    // 所有需要授权
        );

        // 用自定义登录过滤器，替换原有的  （以支持json登录）
        http.addFilterAt(new MyUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 登录处理
        http.formLogin(
                formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(new MyAuthenticationSuccessHandle())    // 认证成功处理器
                        .failureHandler(new MyAuthenticationFailureHandle())    // 认证失败处理器
                        .permitAll()
        );

        // 异常请求处理
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(new MyAuthenticationEntryHandle());  // 未认证处理器，（默认的是重定向到login）
            exception.accessDeniedHandler(new MyAccessDeniedHandle());  // 未授权处理器
        });

        // 会话并发管理
        http.sessionManagement(session -> {
                    session.maximumSessions(1)  // 允许的最多登陆设备（会话窗口）
                            .expiredSessionStrategy(new MySessionInformationExpiredStrategy());     // 登陆设备超限处理器
                }
        );

        // 关闭csrf防御
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    /*
      创建内存信息管理器，
      @return
     *//*
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager();
    }*/


    /**
     * 创建数据库信息管理器
     * @return
     *//*
    @Bean
    public UserDetailsService userDetailsService(){
        return new MyDBUserDetailsManger();
    }*/


    /**
     * 配置密码加密器（自定义用户必配）， 加密输入的密码
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
