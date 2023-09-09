package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;
import com.kumu.service.UserService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult userSignIn(@RequestBody/*获取响应体中的数据*/ User user){
        return userService.register(user);
    }

}