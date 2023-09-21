package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.User;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.exception.SystemException;
import com.kumu.service.LoginService;
import com.kumu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){

        return userService.register(user);
    }
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logOut(){
        return loginService.logout();
    }


}