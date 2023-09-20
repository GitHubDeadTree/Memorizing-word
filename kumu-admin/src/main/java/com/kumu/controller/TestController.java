package com.kumu.controller;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.dto.TestResultDto;
import com.kumu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/testEnd")
    public ResponseResult endTest(){
        return testService.endTest();
    }
    @GetMapping("/getTestRecord/list")
    public ResponseResult getTestRecord_list(){
        return testService.getTestRecord_list();
    }
    @GetMapping("/getTestRecord/detail")
    public ResponseResult getTestRecord_detail(Integer recordId){
        return testService.getTestRecord_detail(recordId);
    }
}