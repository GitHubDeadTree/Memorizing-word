package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.entity.Menu;
import com.kumu.domain.entity.User;
import com.kumu.domain.vo.AdminUserInfoVo;
import com.kumu.domain.vo.RoutersVo;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.exception.SystemException;
import com.kumu.service.BlogLoginService;
import com.kumu.service.LoginService;
import com.kumu.service.MenuService;
import com.kumu.service.TestService;
import com.kumu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/test")
    public ResponseResult Test(){
        return testService.test();
    }


}