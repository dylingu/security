package com.lingu.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingu.mp.config.security.MyDBUserDetailsService;
import com.lingu.mp.mapper.UserMapper;
import com.lingu.mp.pojo.DO.User;
import com.lingu.mp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private MyDBUserDetailsService myDBUserDetailsService;

    @Override
    public void saveDetailUser(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("student")
                .build();
        myDBUserDetailsService.createUser(userDetails);
    }
}
