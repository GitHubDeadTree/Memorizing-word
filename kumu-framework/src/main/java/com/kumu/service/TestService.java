package com.kumu.service;

import com.kumu.domain.ResponseResult;
import com.kumu.domain.dto.TestResultDto;

public interface TestService {
    ResponseResult test();

    ResponseResult start(Integer wordBookId, Integer questionCount, Double percentage, Integer session);

    ResponseResult getTestStatus();

    ResponseResult getQusetion();

    ResponseResult getResult(TestResultDto testResult);

    ResponseResult endTest();

    ResponseResult getTestRecord_list();

    ResponseResult getTestRecord_detail(Integer recordId);
}
