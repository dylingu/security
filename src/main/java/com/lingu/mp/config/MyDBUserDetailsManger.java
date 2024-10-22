package com.lingu.mp.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingu.mp.mapper.UserMapper;
import com.lingu.mp.pojo.DO.User;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyDBUserDetailsManger implements UserDetailsManager, UserDetailsPasswordService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        userMapper.insert(User.builder()
                .name(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))   // 密码加密
                .build());
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * 从数据库中获取信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getName, username));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    true,   // 用户账号是否启用
                    true,   // 用户账号是否过期
                    true,   // 用户凭证是否过期
                    true,   // 用户是否未锁定
                    authorities  // 权限列表
                    );
        }
    }

}
