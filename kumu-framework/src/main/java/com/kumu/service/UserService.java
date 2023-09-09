package com.kumu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;

public interface UserService extends IService<User>{
    ResponseResult register(User user);
}
