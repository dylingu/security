package com.lingu.mp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    /**
     * {@code @Description}  MybatisPlus的分页查询基于MybatisPlus的拦截器实现的
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        // 1、初始化核心插
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2、添加分页插件
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        innerInterceptor.setOverflow(false);
        innerInterceptor.setMaxLimit(1000L); // 设置分页上限
        interceptor.addInnerInterceptor(innerInterceptor); // 添加到拦截器里面
        return interceptor;
    }
}
