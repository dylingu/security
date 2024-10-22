package com.lingu.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingu.mp.pojo.DO.Authority;
import com.lingu.mp.service.AuthorityService;
import com.lingu.mp.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
* @author lingu
* @description 针对表【authority(用户授权管理表)】的数据库操作Service实现
* @createDate 2024-10-22 14:01:48
*/
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
    implements AuthorityService{

}




