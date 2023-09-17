package com.kumu.service.impl;

import com.kumu.domain.ResponseResult;
import com.kumu.service.TestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public ResponseResult test() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return ResponseResult.okResult(formattedDateTime);
    }
}
