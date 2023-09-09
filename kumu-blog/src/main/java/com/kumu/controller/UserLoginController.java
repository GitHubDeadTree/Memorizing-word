package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;
import com.kumu.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult loginOut(){
        System.out.println("controller: 登出");
        return blogLoginService.logout();
    }
}
