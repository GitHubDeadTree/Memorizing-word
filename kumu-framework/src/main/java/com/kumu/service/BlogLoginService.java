package com.kumu.service;


import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
