package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.dto.TestResultDto;
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
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/start")
    public ResponseResult Test(Integer wordBookId, Integer questionCount,Double percentage,Integer session) {
        return testService.start(wordBookId,questionCount,percentage,session);
    }
    @GetMapping("/getTestStatus")
    public ResponseResult getTestStatus(){
        return  testService.getTestStatus();
    }
    @GetMapping("/getQuestion")
    public ResponseResult getQuestion(){
        return  testService.getQusetion();
    }
    @PostMapping("/getResult")
    public ResponseResult getResult(@RequestBody TestResultDto testResult){




        return testService.getResult(testResult);
    }

}