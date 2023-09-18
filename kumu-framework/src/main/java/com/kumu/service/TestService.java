package com.kumu.service;

import com.kumu.domain.ResponseResult;

public interface TestService {
    ResponseResult test();

    ResponseResult start(Integer wordBookId, Integer questionCount, Integer percentage, Integer session);
}
