package com.kumu.service;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

}