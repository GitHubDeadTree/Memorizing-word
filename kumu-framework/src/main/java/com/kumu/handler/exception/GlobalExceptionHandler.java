package com.kumu.handler.exception;

import com.kumu.domain.ResponseResult;
import com.kumu.enums.AppHttpCodeEnum;
import com.kumu.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//统一异常处理，是AOP的思想
@RestControllerAdvice //当任何Controller抛出异常时，都会被GlobalExceptionHandler类中的异常处理方法捕获，处理
//是SpringBoot的注解
@Slf4j //自动生成一个名为 log 的日志对象
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}