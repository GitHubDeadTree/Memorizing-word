package com.kumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kumu.domain.entity.User;
import com.kumu.mapper.UserMapper;
import com.kumu.service.UserService;
import com.kumu.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl extends ServiceImpl<UserMapper, User> implements UtilsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int countByWrapper(LambdaQueryWrapper<User> wrapper) {
        // 调用 MyBatis-Plus 提供的 count 方法获取满足条件的记录数量
        return userMapper.selectCount(wrapper);
    }
}