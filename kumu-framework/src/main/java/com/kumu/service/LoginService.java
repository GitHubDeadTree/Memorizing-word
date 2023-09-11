package com.kumu.service;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;
import org.springframework.stereotype.Service;



public interface LoginService {
    ResponseResult logout();

    ResponseResult login(User user);


}