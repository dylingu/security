package com.lingu.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingu.mp.pojo.DO.User;

public interface UserService extends IService<User> {
    void saveDetailUser(User user);
}
