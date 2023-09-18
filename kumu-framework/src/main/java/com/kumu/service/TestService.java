package com.kumu.service;

import com.kumu.domain.ResponseResult;

public interface TestService {
    ResponseResult test();

    ResponseResult start(Integer wordBookId, Integer questionCount, Double percentage, Integer session);

    ResponseResult getTestStatus();
}
